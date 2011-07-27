/*
 * Copyright (C) 2010 Andrey Yeremenok (eav1986__at__gmail__com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

/**
 *
 */
package org.formbuilder;

import org.formbuilder.mapping.BeanMapping;
import org.formbuilder.mapping.BeanMappingContext;
import org.formbuilder.mapping.MappingRules;
import org.formbuilder.mapping.beanmapper.GridBagMapper;
import org.formbuilder.mapping.beanmapper.PropertyNameBeanMapper;
import org.formbuilder.mapping.beanmapper.SampleBeanMapper;
import org.formbuilder.mapping.change.ValidateOnChange;
import org.formbuilder.mapping.exception.MappingException;
import org.formbuilder.mapping.form.FormFactories;
import org.formbuilder.mapping.form.FormFactory;
import org.formbuilder.mapping.metadata.CombinedMetaData;
import org.formbuilder.mapping.metadata.MetaData;
import org.formbuilder.mapping.typemapper.GetterConfig;
import org.formbuilder.mapping.typemapper.GetterMapper;
import org.formbuilder.mapping.typemapper.impl.BooleanToCheckboxMapper;
import org.formbuilder.mapping.typemapper.impl.DateToSpinnerMapper;
import org.formbuilder.mapping.typemapper.impl.NumberToSpinnerMapper;
import org.formbuilder.mapping.typemapper.impl.StringToTextFieldMapper;
import org.formbuilder.util.MethodRecorder;
import org.formbuilder.util.Reflection;
import org.formbuilder.validation.ValidationMarker;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.*;
import javax.validation.Validator;
import java.awt.*;
import java.math.BigDecimal;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The entry point to form building. Collects configuration and builds {@link Form}s using it. <br> <h1>Usage:</h1>
 * <p/>
 * <pre>
 * Form&lt;Person&gt; form = FormBuilder.map( Person.class ).with( new SampleBeanMapper&lt;Person&gt;()
 *                   {
 *                       &#064;Override
 *                       protected JComponent mapBean( Person beanSample, SampleContext&lt;Person&gt; ctx )
 *                       {
 *                           Box box = Box.createHorizontalBox();
 *                           box.add( ctx.label( beanSample.getSecondName() ) );
 *                           box.add( ctx.editor( beanSample.getSecondName() ) );
 *                           return box;
 *                       }
 *                   } ).buildForm();
 * JComponent formPanel = form.asComponent();
 * </pre>
 *
 * @author aeremenok 2010
 */
@NotThreadSafe
public class FormBuilder<B>
{
// ------------------------------ FIELDS ------------------------------
    private final Class<B> beanClass;
    private final MappingRules mappingRules = new MappingRules();
    @Nonnull
    private BeanMapper<B> beanMapper = new GridBagMapper<B>();
    @Nonnull
    private MetaData metaData = new CombinedMetaData();
    @Nonnull
    private FormFactory formFactory = FormFactories.REPLICATING;
    private boolean doValidation = true;

// -------------------------- STATIC METHODS --------------------------

    /**
     * Starts building of the form for the given class.
     *
     * @param <T>       bean type
     * @param beanClass bean class object
     * @return builder instance, configured for the given class
     */
    @Nonnull
    public static <T> FormBuilder<T> map( @Nonnull final Class<T> beanClass )
    {
        return new FormBuilder<T>( checkNotNull( beanClass ) );
    }

// --------------------------- CONSTRUCTORS ---------------------------

    private FormBuilder( final Class<B> beanClass )
    {
        this.beanClass = beanClass;
    }

// -------------------------- OTHER METHODS --------------------------

    /**
     * Allows a to provide a custom way to extract property attributes
     *
     * @param metaData attribute extractor
     * @return this builder
     */
    @Nonnull
    public FormBuilder<B> ask( @Nonnull final MetaData metaData )
    {
        this.metaData = checkNotNull( metaData );
        return this;
    }

    /**
     * Does the building of editor components and their layout on the form component. <br>
     * <u>This method should be called in Event Dispatch Thread</u>
     *
     * @return an instance of the {@link Form}, ready to be added to swing {@link Container}s
     */
    @Nonnull
    public Form<B> buildForm()
    { // todo pass a copy of mapping rules
        final BeanMapping beanMapping = new BeanMapping();
        final BeanMappingContext<B> context = new BeanMappingContext<B>( beanMapping,
                beanClass,
                mappingRules,
                doValidation,
                metaData );
        final JComponent panel = beanMapper.map( context );
        return formFactory.createForm( panel, beanClass, beanMapping );
    }

    /**
     * Turns on/off the validation. <u>By default, the validation is turned <b>on</b></u>
     *
     * @param doValidation true if the form should perform validation after changing the editor components
     * @return this builder
     * @see Validator
     * @see ValidationMarker
     * @see ValidateOnChange
     */
    @Nonnull
    public FormBuilder<B> doValidation( final boolean doValidation )
    {
        this.doValidation = doValidation;
        return this;
    }

    /**
     * Use a custom {@link FormFactory} to build forms.
     *
     * @param formFactory a factory to use
     * @return this builder
     * @see FormFactories
     */
    @Nonnull
    public FormBuilder<B> formsOf( @Nonnull final FormFactory formFactory )
    {
        this.formFactory = checkNotNull( formFactory );
        return this;
    }

    /**
     * Binds types of beanmapper properties to custom editor components.<br> By default, {@link
     * StringToTextFieldMapper}, {@link NumberToSpinnerMapper}, {@link DateToSpinnerMapper} and {@link
     * BooleanToCheckboxMapper} are already registered.<br> <br> Primitive and wrapper types are considered the same
     * way. For example, if you pass a mapper of the {@link Integer} class, it will also be used for mapping of
     * <code>int</code> properties. <br> <br> During the mapping, if a mapper for some property typemapper cannot be
     * found, an attempt to find a mapper for its supertype is performed. If it is failed, {@link MappingException} is
     * raised, and by default, the propery is skipped. That means, for example, that {@link NumberToSpinnerMapper} suits
     * for int, long, {@link BigDecimal}, etc...
     *
     * @param typeMappers mappers for each custom type
     * @return this builder
     * @see MappingRules
     */
    @Nonnull
    public FormBuilder<B> use( @Nonnull final TypeMapper... typeMappers )
    {
        for ( final TypeMapper typeMapper : typeMappers )
        {
            this.mappingRules.addMapper( checkNotNull( typeMapper ) );
        }
        return this;
    }

    /**
     * Allows to perform a <u>checked</u> mapping for properties, specified by user. For example, the following code
     * orders to use a <code>StringToTextAreaMapper</code> for property description, while other {@link String}
     * properties are mapped by default:
     * <p/>
     * <pre>
     * Form&lt;Person&gt; form = FormBuilder.map( Person.class ).useForGetters( new GetterMapper&lt;Person&gt;()
     *                   {
     *                       &#064;Override
     *                       protected void mapGetters( Person beanSample, GetterConfig config )
     *                       {
     *                           config.use( beanSample.getDescription(), new StringToTextAreaMapper() );
     *                       }
     *                   } ).buildForm();
     * </pre>
     *
     * @param getterMapper an implementation of {@link GetterMapper}, where the user can specify property bindings
     * @return this builder
     * @see MappingRules
     */
    @Nonnull
    public FormBuilder<B> useForGetters( @Nonnull final GetterMapper<B> getterMapper )
    {
        final MethodRecorder methodRecorder = new MethodRecorder();
        final GetterConfig config = new GetterConfig( mappingRules, methodRecorder );
        getterMapper.mapGetters( Reflection.createProxy( beanClass, methodRecorder ), config );
        return this;
    }

    /**
     * Allows to perform an <u>unchecked</u> mapping for a property, specified by user. For example, the following code
     * orders to use a <code>StringToTextAreaMapper</code> for property description, while other {@link String}
     * properties are mapped by default:
     * <p/>
     * <pre>
     * Form&lt;Person&gt; form = FormBuilder.map( Person.class )
     *          .useForProperty( &quot;description&quot;, new StringToTextAreaMapper() ).buildForm();
     * </pre>
     *
     * @param propertyName   the name of beanmapper property
     * @param propertyMapper the mapper to use for it
     * @return this builder
     * @see MappingRules
     */
    @Nonnull
    public FormBuilder<B> useForProperty( @Nonnull final String propertyName,
                                          @Nonnull final TypeMapper propertyMapper )
    {
        this.mappingRules.addMapper( checkNotNull( propertyName ), checkNotNull( propertyMapper ) );
        return this;
    }

    /**
     * Allows to control the building of the entire form component using a custom {@link BeanMapper}.
     *
     * @param beanMapper the custom mapper to use
     * @return this builder
     * @see BeanMapper
     * @see GridBagMapper
     * @see SampleBeanMapper
     * @see PropertyNameBeanMapper
     */
    @Nonnull
    public FormBuilder<B> with( @Nonnull final BeanMapper<B> beanMapper )
    {
        this.beanMapper = checkNotNull( beanMapper );
        return this;
    }
}
