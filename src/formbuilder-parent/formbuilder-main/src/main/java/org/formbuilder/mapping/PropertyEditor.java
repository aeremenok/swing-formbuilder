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

import org.formbuilder.TypeMapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.beans.PropertyDescriptor;

/**
 * Holds an editor cmponent and a mapper, that provides access to it.
 *
 * @author eav Date: 31.07.2010 Time: 17:19:34
 * @param <C> editor component type
 * @param <V> value type
 */
public class PropertyEditor<C extends JComponent, V>
{
// ------------------------------ FIELDS ------------------------------
    private final C editorComponent;
    private final TypeMapper<C, V> mapper;
    private final PropertyDescriptor descriptor;
    private final BeanMapping beanMapping;

// --------------------------- CONSTRUCTORS ---------------------------

    public PropertyEditor( @Nonnull final C editorComponent,
                           @Nonnull final TypeMapper<C, V> mapper,
                           @Nonnull final PropertyDescriptor descriptor,
                           @Nonnull final BeanMapping beanMapping )
    {
        this.editorComponent = editorComponent;
        this.mapper = mapper;
        this.descriptor = descriptor;
        this.beanMapping = beanMapping;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    @Nonnull
    public PropertyDescriptor getDescriptor()
    {
        return descriptor;
    }

    @Nonnull
    public C getEditorComponent()
    {
        return editorComponent;
    }

    @Nonnull
    public TypeMapper<C, V> getMapper()
    {
        return mapper;
    }

// -------------------------- OTHER METHODS --------------------------

    @Nonnull
    public JLabel getLabel()
    {
        return beanMapping.getLabel( descriptor );
    }

    @Nullable
    public V getValue()
    {
        return mapper.getValue( editorComponent );
    }

    public void setValue( @Nullable final V value )
    {
        mapper.setValue( editorComponent, value );
    }
}
