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

import org.formbuilder.validation.ValidationMarker;

/**
 * Used when the validation is tured off, and there is no need of processing the value changes.
 *
 * @author eav Date: Aug 3, 2010 Time: 12:05:47 AM
 */
public class DoNothingOnChange
        implements ChangeHandler
{
    public static final DoNothingOnChange INSTANCE = new DoNothingOnChange();

    public void onChange( final ValidationMarker... validationMarkers )
    {
    }
}
