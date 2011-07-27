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

import org.formbuilder.TypeMapper;

import javax.annotation.Nonnull;
import java.beans.PropertyDescriptor;

/** @author aeremenok Date: 30.07.2010 Time: 17:12:15 */
public class InvalidTypeMappingException
        extends MappingException
{
    private final Class expectedType;
    private final TypeMapper mapper;

    public InvalidTypeMappingException( @Nonnull final PropertyDescriptor descriptor,
                                        @Nonnull final TypeMapper mapper )
    {
        super( message( descriptor, mapper ), descriptor );
        expectedType = descriptor.getPropertyType();
        this.mapper = mapper;
    }

    private static String message( final PropertyDescriptor descriptor,
                                   final TypeMapper mapper )
    {
        return "Property " + descriptor.getName() + " of typemapper " + descriptor.getPropertyType() + " cannot be mapped using " + mapper + " since it maps " + mapper
                .getValueClass();
    }

    @Nonnull
    public Class getActualType()
    {
        return mapper.getValueClass();
    }

    @Nonnull
    public Class getExpectedType()
    {
        return expectedType;
    }
}
