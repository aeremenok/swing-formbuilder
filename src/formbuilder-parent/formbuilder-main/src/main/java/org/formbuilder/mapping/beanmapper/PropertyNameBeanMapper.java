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

import org.formbuilder.BeanMapper;
import org.formbuilder.mapping.BeanMappingContext;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.*;

/**
 * Allows user to provide his own component, and gives the ability to inject labels and editor components by specifying
 * the property names.
 *
 * @author aeremenok Date: Aug 3, 2010 Time: 1:09:26 PM
 * @param <B> bean type
 */
// todo is a middleman?
@NotThreadSafe
public abstract class PropertyNameBeanMapper<B>
        implements BeanMapper<B>
{
// ------------------------ INTERFACE METHODS ------------------------

// --------------------- Interface BeanMapper ---------------------

    public JComponent map( @Nonnull final BeanMappingContext<B> context )
    {
        return mapBean( new PropertyNameContext<B>( context ) );
    }

// -------------------------- OTHER METHODS --------------------------

    /**
     * @param context provides editor components and labels for property names
     * @return user's custom form component
     */
    protected abstract JComponent mapBean( final PropertyNameContext<B> context );
}
