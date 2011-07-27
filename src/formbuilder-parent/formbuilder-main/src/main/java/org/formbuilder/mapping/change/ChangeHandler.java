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
package org.formbuilder.mapping.change;

import org.formbuilder.TypeMapper;
import org.formbuilder.validation.ValidationMarker;

import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.*;

/**
 * Should be used to propagate the changes from the editor component to mapping
 *
 * @author aeremenok Date: 29.07.2010 Time: 17:18:45
 * @see TypeMapper#handleChanges(JComponent, ChangeHandler)
 */
@NotThreadSafe
public interface ChangeHandler
{
    /**
     * Should be called when the editor component changed its value. <br>
     * <u>This method should be called in Event Dispatch Thread</u>
     *
     * @param validationMarkers callbacks, that process the validation results, after the value change is validated
     */
    void onChange( final ValidationMarker... validationMarkers );
}
