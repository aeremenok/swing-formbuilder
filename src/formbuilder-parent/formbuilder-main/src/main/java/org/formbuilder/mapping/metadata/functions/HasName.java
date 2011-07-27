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
package org.formbuilder.mapping.metadata.functions;

import com.google.common.base.Predicate;

import javax.annotation.Nonnull;
import java.beans.PropertyDescriptor;

/** @author aeremenok Date: Aug 31, 2010 Time: 11:27:26 AM */
public class HasName
        implements Predicate<PropertyDescriptor>
{
// ------------------------------ FIELDS ------------------------------
    private final String name;

// --------------------------- CONSTRUCTORS ---------------------------

    public HasName( @Nonnull final String name )
    {
        this.name = name;
    }

// ------------------------ INTERFACE METHODS ------------------------

// --------------------- Interface Predicate ---------------------

    public boolean apply( @Nonnull final PropertyDescriptor descriptor )
    {
        return descriptor.getName().equals( name );
    }
}
