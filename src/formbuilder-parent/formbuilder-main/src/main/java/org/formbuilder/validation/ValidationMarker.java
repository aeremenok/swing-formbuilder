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

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.*;
import javax.validation.Validator;

/**
 * Marks an editor field as valid/invalid according to constraint violations, emitted by {@link Validator}
 *
 * @author aeremenok Date: 29.07.2010 Time: 17:40:48
 */
@NotThreadSafe
public interface ValidationMarker
{
    /**
     * Does the component decoration. <br>
     * <u>This method should be called in Event Dispatch Thread</u>
     *
     * @param <B> bean type
     * @param <C> editor component type
     * @param <V> value type
     *
     * @param validationEvent provides validation results and components that can be markes
     */
    <B, C extends JComponent, V> void markViolations( @Nonnull ValidationEvent<B, C, V> validationEvent );
}
