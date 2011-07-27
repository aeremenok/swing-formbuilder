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
package org.formbuilder.mapping.beanmapper;

import org.formbuilder.BeanMapper;
import org.formbuilder.mapping.BeanMappingContext;
import org.formbuilder.util.MethodRecorder;
import org.formbuilder.util.Reflection;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.*;

/**
 * Allows user to provide his own component, and gives the ability to inject labels and editor components by calling
 * getters of the sample bean instance
 *
 * @author aeremenok 2010
 * @param <B> bean type
 */
// todo is a middleman?
@NotThreadSafe
public abstract class SampleBeanMapper<B>
        implements BeanMapper<B>
{
// ------------------------ INTERFACE METHODS ------------------------

// --------------------- Interface BeanMapper ---------------------

    @Nonnull
    public JComponent map( @Nonnull final BeanMappingContext<B> context )
    {
        final MethodRecorder methodRecorder = new MethodRecorder();
        final SampleContext<B> sampleContext = new SampleContext<B>( context, methodRecorder );
        final B beanSample = Reflection.createProxy( context.getBeanClass(), methodRecorder );
        return mapBean( beanSample, sampleContext );
    }

// -------------------------- OTHER METHODS --------------------------

    /**
     * @param sample  a cglib-proxy, which records method calls
     * @param context provides editor components and labels for called getters of sample
     * @return user's custom form component
     */
    @Nonnull
    protected abstract JComponent mapBean( @Nonnull final B sample,
                                           @Nonnull final SampleContext<B> context );
}
