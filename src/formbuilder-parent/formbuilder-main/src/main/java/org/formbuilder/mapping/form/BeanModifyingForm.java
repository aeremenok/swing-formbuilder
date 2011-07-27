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
package org.formbuilder.mapping.form;

import org.formbuilder.Form;
import org.formbuilder.mapping.BeanMapping;
import org.formbuilder.util.Reflection;
import org.formbuilder.util.SwingUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;

/**
 * A {@link Form} that changes the given bean instance, or allocates a new one if a given instance is null
 *
 * @author aeremenok Date: Aug 31, 2010 Time: 12:49:55 PM
 */
public class BeanModifyingForm<B>
        implements Form<B>
{
// ------------------------------ FIELDS ------------------------------
    @Nullable
    private B bean;
    private final JComponent panel;
    private final BeanMapping beanMapping;
    private final Class<B> beanClass;

// --------------------------- CONSTRUCTORS -----------t----------------

    public BeanModifyingForm( @Nonnull final JComponent panel,
                              @Nonnull final BeanMapping beanMapping,
                              @Nonnull final Class<B> beanClass )
    {
        this.panel = panel;
        this.beanMapping = beanMapping;
        this.beanClass = beanClass;
    }

// ------------------------ INTERFACE METHODS ------------------------

// --------------------- Interface Form ---------------------

    public JComponent asComponent()
    {
        return panel;
    }

    public B getValue()
    {
        if ( bean == null )
        {
            bean = Reflection.newInstance( beanClass );
        }
        beanMapping.setBeanValues( bean );
        // todo set null if all values are empty?
        return bean;
    }

    public void setValue( @Nullable final B bean )
    {
        SwingUtil.checkForEventDispatchThread();
        this.bean = bean;
        beanMapping.setComponentValues( bean );
    }
}
