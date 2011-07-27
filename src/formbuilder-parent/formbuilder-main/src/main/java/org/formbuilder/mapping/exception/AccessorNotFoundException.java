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
import java.lang.reflect.Method;

/** @author aeremenok Date: Aug 3, 2010 Time: 12:06:57 PM */
public class AccessorNotFoundException
        extends MappingException
{
    private final Method readMethod;

    public AccessorNotFoundException( @Nonnull final Method readMethod )
    {
        super( readMethod + " is not an accessor method of a bean", null );
        this.readMethod = readMethod;
    }

    @Nonnull
    public Method getReadMethod()
    {
        return readMethod;
    }
}
