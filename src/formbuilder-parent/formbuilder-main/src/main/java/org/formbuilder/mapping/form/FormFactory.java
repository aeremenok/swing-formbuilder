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

import javax.annotation.Nonnull;
import javax.swing.*;

/**
 * Creates forms of specific types, when mapping is complete.
 *
 * @author aeremenok Date: Aug 31, 2010 Time: 12:44:03 PM
 * @see Form
 */
public interface FormFactory
{
    <B> Form<B> createForm( @Nonnull final JComponent panel,
                            @Nonnull final Class<B> beanClass,
                            @Nonnull final BeanMapping beanMapping );
}
