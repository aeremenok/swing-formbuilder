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
package org.formbuilder.mapping.metadata.sort;

import org.formbuilder.mapping.metadata.MetaData;

import javax.annotation.Nonnull;
import java.beans.PropertyDescriptor;

/**
 * Holds a property descriptor and its order according to {@link MetaData}
 *
 * @author eav Date: 01.08.2010 Time: 22:53:40
 * @see MetaData#getOrder(PropertyDescriptor)
 */
public class OrderedPropertyDescriptor
{
// ------------------------------ FIELDS ------------------------------
    private final int order;
    private final PropertyDescriptor descriptor;

// --------------------------- CONSTRUCTORS ---------------------------

    public OrderedPropertyDescriptor( @Nonnull final PropertyDescriptor descriptor,
                                      final int order )
    {
        this.order = order;
        this.descriptor = descriptor;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    @Nonnull
    public PropertyDescriptor getDescriptor()
    {
        return descriptor;
    }

    public int getOrder()
    {
        return order;
    }
}
