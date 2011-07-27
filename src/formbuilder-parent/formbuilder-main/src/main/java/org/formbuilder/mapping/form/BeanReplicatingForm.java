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
package org.formbuilder.mapping.form;

import org.formbuilder.Form;
import org.formbuilder.mapping.BeanMapping;
import org.formbuilder.util.Reflection;
import org.formbuilder.util.SwingUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;

/**
 * A {@link Form} that allocates a new instance of beanmapper each time it is requested for a changed value.
 *
 * @author aeremenok 2010
 * @param <B> bean type
 */
public class BeanReplicatingForm<B>
        implements Form<B>
{
// ------------------------------ FIELDS ------------------------------
    private final JComponent panel;
    private final BeanMapping beanMapping;
    private final Class<B> beanClass;
    @Nullable
    private Class<? extends B> actualBeanClass;

// --------------------------- CONSTRUCTORS ---------------------------

    public BeanReplicatingForm( @Nonnull final JComponent panel,
                                @Nonnull final Class<B> beanClass,
                                @Nonnull final BeanMapping beanMapping )
    {
        this.panel = panel;
        this.beanMapping = beanMapping;
        this.beanClass = beanClass;
    }

// ------------------------ INTERFACE METHODS ------------------------

// --------------------- Interface Form ---------------------

    @Nonnull
    public JComponent asComponent()
    {
        return panel;
    }

    /** @return a new beanmapper instance with changed values */
    @Nonnull
    public B getValue()
    {
        final boolean interfaceMapped = actualBeanClass != null;
        final B newBean = Reflection.newInstance( interfaceMapped ? actualBeanClass : beanClass );
        beanMapping.setBeanValues( newBean );
        return newBean;
    }

    /**
     * Propagates changes to mapping, but doesn't remember a given beanmapper instance.
     *
     * @param bean a value source
     */
    @SuppressWarnings( {"unchecked"} )
    public void setValue( @Nullable final B bean )
    {
        SwingUtil.checkForEventDispatchThread();
        beanMapping.setComponentValues( bean );
        if ( bean != null && beanClass.isInterface() )
        {
            this.actualBeanClass = (Class<? extends B>) bean.getClass();
        }
    }
}
