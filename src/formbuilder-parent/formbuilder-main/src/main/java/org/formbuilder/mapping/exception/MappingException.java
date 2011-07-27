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
package org.formbuilder.mapping.exception;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.beans.PropertyDescriptor;

/** @author aeremenok Date: 30.07.2010 Time: 17:04:09 */
public class MappingException
        extends RuntimeException
{
    private final PropertyDescriptor descriptor;

    public MappingException( @Nonnull final String message,
                             @Nullable final PropertyDescriptor descriptor )
    {
        super( message );
        this.descriptor = descriptor;
    }

    @Nullable
    public PropertyDescriptor getDescriptor()
    {
        return descriptor;
    }
}
