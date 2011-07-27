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

package org.formbuilder.validation;

import org.formbuilder.mapping.PropertyEditor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.util.Set;

/**
 * Contains validation results.
 *
 * @author aeremenok Date: 19.08.2010 Time: 18:10:47
 * @param <B> bean type
 * @param <C> editor component type
 * @param <V> value type
 */
public class ValidationEvent<B, C extends JComponent, V>
{
// ------------------------------ FIELDS ------------------------------
    private final PropertyEditor<C, V> propertyEditor;
    private final Set<ConstraintViolation<B>> violations;
    private final V newValue;

// --------------------------- CONSTRUCTORS ---------------------------

    public ValidationEvent( @Nonnull final PropertyEditor<C, V> propertyEditor,
                            @Nonnull final Set<ConstraintViolation<B>> violations,
                            @Nullable final V newValue )
    {
        this.propertyEditor = propertyEditor;
        this.violations = violations;
        this.newValue = newValue;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    /** @return changed property value */
    @Nullable
    public V getNewValue()
    {
        return newValue;
    }

    /**
     * @return constraint violations of a changed property value
     *
     * @see Validator#validateValue(Class, String, Object, Class[])
     */
    @Nonnull
    public Set<ConstraintViolation<B>> getViolations()
    {
        return violations;
    }

// -------------------------- OTHER METHODS --------------------------

    /**
     * @return a descriptor of a changed property
     *
     * @see BeanInfo#getPropertyDescriptors()
     */
    @Nonnull
    public PropertyDescriptor getDescriptor()
    {
        return propertyEditor.getDescriptor();
    }

    /** @return an editor component of a changed property */
    @Nonnull
    public C getEditorComponent()
    {
        return propertyEditor.getEditorComponent();
    }

    /** @return a label of a changed property */
    @Nonnull
    public JLabel getLabel()
    {
        return propertyEditor.getLabel();
    }
}
