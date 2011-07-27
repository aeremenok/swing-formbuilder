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

/** @author aeremenok Date: Aug 3, 2010 Time: 1:28:52 PM */
public class PropertyNotFoundException
        extends MappingException
{
    private final Class beanClass;
    private final String propertyName;

    public PropertyNotFoundException( @Nonnull final Class beanClass,
                                      @Nonnull final String propertyName )
    {
        super( "Cannot find property " + propertyName + " of class " + beanClass, null );
        this.beanClass = beanClass;
        this.propertyName = propertyName;
    }

    @Nonnull
    public Class getBeanClass()
    {
        return beanClass;
    }

    @Nonnull
    public String getPropertyName()
    {
        return propertyName;
    }
}
