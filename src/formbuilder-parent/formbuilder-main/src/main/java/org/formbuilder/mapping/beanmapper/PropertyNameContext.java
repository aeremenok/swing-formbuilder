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

package org.formbuilder.mapping.beanmapper;

import org.formbuilder.TypeMapper;
import org.formbuilder.mapping.BeanMappingContext;
import org.formbuilder.mapping.exception.InvalidTypeMappingException;
import org.formbuilder.mapping.exception.PropertyNotFoundException;
import org.formbuilder.mapping.exception.UnmappedTypeException;
import org.formbuilder.mapping.metadata.MetaData;
import org.formbuilder.util.Reflection;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.beans.PropertyDescriptor;

/**
 * Provides editor components and labels for property names.
 *
 * @author aeremenok Date: Aug 18, 2010 Time: 4:32:57 PM
 * @param <B> bean type
 */
public class PropertyNameContext<B>
{
// ------------------------------ FIELDS ------------------------------
    private final BeanMappingContext<B> beanMappingContext;

// --------------------------- CONSTRUCTORS ---------------------------

    public PropertyNameContext( @Nonnull final BeanMappingContext<B> beanMappingContext )
    {
        this.beanMappingContext = beanMappingContext;
    }

// -------------------------- OTHER METHODS --------------------------

    /**
     * @param propertyName a name of a property to be mapped
     * @return an editor component for a property, one instance per property
     *
     * @throws PropertyNotFoundException   the bean class has no property with given name
     * @throws UnmappedTypeException       cannot find a type mapper for a given property name
     * @throws InvalidTypeMappingException found a type mapper that has a wrong type
     * @see BeanMappingContext#getEditor(PropertyDescriptor)
     * @see TypeMapper#createEditorComponent()
     * @see TypeMapper#getValueClass()
     */
    @Nonnull
    public JComponent editor( @Nonnull final String propertyName )
            throws
            PropertyNotFoundException,
            InvalidTypeMappingException,
            UnmappedTypeException
    {
        return beanMappingContext.getEditor( getDescriptor( propertyName ) );
    }

    @Nonnull
    private PropertyDescriptor getDescriptor( @Nonnull final String propertyName )
            throws
            PropertyNotFoundException
    {
        final Class<B> beanClass = beanMappingContext.getBeanClass();
        return Reflection.getDescriptor( beanClass, propertyName );
    }

    /**
     * @param propertyName a name of property to be mapped
     * @return a label for property, one instance per property
     *
     * @throws PropertyNotFoundException the bean class has no property with given name
     * @see BeanMappingContext#getLabel(PropertyDescriptor)
     * @see MetaData#getTitle(PropertyDescriptor)
     */
    @Nonnull
    public JLabel label( @Nonnull final String propertyName )
            throws
            PropertyNotFoundException
    {
        return beanMappingContext.getLabel( getDescriptor( propertyName ) );
    }
}

