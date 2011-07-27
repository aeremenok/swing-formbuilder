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
import org.formbuilder.util.Reflection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

/**
 * Maps beanmapper properties to their editors.
 *
 * @author aeremenok Date: 28.07.2010 Time: 13:47:07
 */
public class BeanMapping
{
// ------------------------------ FIELDS ------------------------------
    private final Map<PropertyDescriptor, PropertyEditor> propertyEditors = new HashMap<PropertyDescriptor, PropertyEditor>();
    private final Map<PropertyDescriptor, JLabel> labels = new HashMap<PropertyDescriptor, JLabel>();

// -------------------------- OTHER METHODS --------------------------

    @SuppressWarnings( {"unchecked"} )
    public PropertyEditor addEditor( @Nonnull final PropertyDescriptor descriptor,
                                     @Nonnull final JComponent editorComponent,
                                     @Nonnull final TypeMapper mapper )
    {
        final PropertyEditor propertyEditor = new PropertyEditor( editorComponent, mapper, descriptor, this );
        propertyEditors.put( descriptor, propertyEditor );
        return propertyEditor;
    }

    public void addLabel( @Nonnull final PropertyDescriptor descriptor,
                          @Nonnull final JLabel label )
    {
        labels.put( descriptor, label );
    }

    @Nullable
    public JComponent getEditorComponent( @Nonnull final PropertyDescriptor descriptor )
    {
        final PropertyEditor propertyEditor = propertyEditors.get( descriptor );
        if ( propertyEditor == null )
        {
            return null;
        }
        return propertyEditor.getEditorComponent();
    }

    @Nullable
    public JLabel getLabel( @Nonnull final PropertyDescriptor descriptor )
    {
        return labels.get( descriptor );
    }

    /**
     * Propagate changes from editor components to a beanmapper
     *
     * @param bean a value destination
     */
    public void setBeanValues( @Nonnull final Object bean )
    {
        for ( final Map.Entry<PropertyDescriptor, PropertyEditor> entry : propertyEditors.entrySet() )
        {
            final PropertyEditor propertyEditor = entry.getValue();
            final PropertyDescriptor propertyDescriptor = entry.getKey();

            final Object propertyValue = propertyEditor.getValue();
            Reflection.setValue( bean, propertyValue, propertyDescriptor );
        }
    }

    /**
     * Propagate changes from a beanmapper to editor components.
     *
     * @param bean a value source
     */
    @SuppressWarnings( {"unchecked"} )
    public void setComponentValues( @Nullable final Object bean )
    {
        for ( final Map.Entry<PropertyDescriptor, PropertyEditor> entry : propertyEditors.entrySet() )
        {
            final PropertyEditor propertyEditor = entry.getValue();
            final PropertyDescriptor propertyDescriptor = entry.getKey();

            final Object propertyValue = Reflection.getValue( propertyDescriptor, bean );
            propertyEditor.setValue( propertyValue );
        }
    }
}
