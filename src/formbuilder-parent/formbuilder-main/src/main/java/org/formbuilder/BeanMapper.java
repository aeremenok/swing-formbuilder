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

import org.formbuilder.mapping.BeanMappingContext;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.*;

/**
 * Creates the panel and perform the layout of editors on it.
 *
 * @author aeremenok 2010
 * @param <B> bean type
 */
@NotThreadSafe
public interface BeanMapper<B>
{
    /**
     * Transforms a data from context to a swing form component <br>
     * <u>This method should be called in Event Dispatch Thread</u>
     *
     * @param context provides the data to assist editor creation
     * @return a panel with editor components
     */
    @Nonnull
    JComponent map( @Nonnull final BeanMappingContext<B> context );
}
