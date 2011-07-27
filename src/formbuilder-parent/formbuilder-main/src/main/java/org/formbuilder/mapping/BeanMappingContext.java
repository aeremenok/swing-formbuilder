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

package org.formbuilder.mapping;

import org.formbuilder.BeanMapper;
import org.formbuilder.TypeMapper;
import org.formbuilder.mapping.change.ChangeObservation;
import org.formbuilder.mapping.exception.InvalidTypeMappingException;
import org.formbuilder.mapping.exception.UnmappedTypeException;
import org.formbuilder.mapping.metadata.MetaData;
import org.formbuilder.mapping.metadata.sort.OrderedPropertyDescriptor;
import org.formbuilder.mapping.metadata.sort.PropertySorter;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.util.List;

/**
 * Provides the data needed to assist the editor creation when {@link BeanMapper#map(BeanMappingContext)} is called.
 *
 * @author aeremenok Date: Aug 18, 2010 Time: 1:57:48 PM
 * @param <B> bean type
 * @see BeanMapper
 */
public class BeanMappingContext<B>
{
// ------------------------------ FIELDS ------------------------------
    protected final Class<B> beanClass;
    protected final MappingRules mappingRules;
    protected final ComponentFactory componentFactory;
    protected final PropertySorter sorter;
    protected final ChangeObservation changeObservation;
    protected final BeanMapping beanMapping;

// --------------------------- CONSTRUCTORS ---------------------------

    public BeanMappingContext( final BeanMapping beanMapping,
                               final Class<B> beanClass,
                               final MappingRules mappingRules,
                               final boolean doValidation,
                               final MetaData metaData )
    {
        this.beanMapping = beanMapping;
        this.beanClass = beanClass;
        this.mappingRules = mappingRules;
        this.changeObservation = new ChangeObservation( doValidation );

        this.componentFactory = new ComponentFactory( metaData );
        this.sorter = new PropertySorter( metaData );
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    /** @return a class, which mapping is assisted */
    @Nonnull
    public Class<B> getBeanClass()
    {
        return beanClass;
    }

// -------------------------- OTHER METHODS --------------------------

    /**
     * @return property descriptors of a bean class, decorated with order property and sorted according to it
     *
     * @see OrderedPropertyDescriptor
     * @see BeanInfo#getPropertyDescriptors()
     * @see MetaData#getOrder(PropertyDescriptor)
     */
    @Nonnull
    public List<OrderedPropertyDescriptor> getActiveSortedDescriptors()
    {
        return sorter.activeSortedDescriptors( beanClass );
    }

    /**
     * Returns an editor component for a given property. If this method is called first time, the {@link TypeMapper} for
     * a property is asked to create a new editor component. After that, the component is saved in the local cache.
     *
     * @param descriptor a bean property
     * @return an editor component for a given property
     *
     * @throws InvalidTypeMappingException the type mapper, assigned for a given property has wrong type
     * @throws UnmappedTypeException       cannot find a type mapper for a given property
     */
    @Nonnull
    public JComponent getEditor( @Nonnull final PropertyDescriptor descriptor )
            throws
            InvalidTypeMappingException,
            UnmappedTypeException
    {
        JComponent editorComponent = beanMapping.getEditorComponent( descriptor );
        if ( editorComponent == null )
        {
            final TypeMapper mapper = mappingRules.getMapper( descriptor );
            editorComponent = componentFactory.createEditor( descriptor, mapper );
            changeObservation.observe( beanMapping.addEditor( descriptor, editorComponent, mapper ) );
        }
        return editorComponent;
    }

    /**
     * Returns an editor component for a given property. If this method is called first time, the label is created.
     * After that, the label is saved in the local cache.
     *
     * @param descriptor a bean property
     * @return a label, that contains a title for a given property
     *
     * @see MetaData#getTitle(PropertyDescriptor)
     */
    @Nonnull
    public JLabel getLabel( @Nonnull final PropertyDescriptor descriptor )
    {
        JLabel label = beanMapping.getLabel( descriptor );
        if ( label == null )
        {
            label = componentFactory.createLabel( descriptor );
            beanMapping.addLabel( descriptor, label );
        }
        return label;
    }
}
