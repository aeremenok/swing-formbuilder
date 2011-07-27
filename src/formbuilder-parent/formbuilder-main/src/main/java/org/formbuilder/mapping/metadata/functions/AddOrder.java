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

import com.google.common.base.Function;
import org.formbuilder.mapping.metadata.MetaData;
import org.formbuilder.mapping.metadata.MetaDataUser;
import org.formbuilder.mapping.metadata.sort.OrderedPropertyDescriptor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.beans.PropertyDescriptor;

/**
 * Decorates a property descriptor with its order or Integer.MAX_VALUE if order isn't specified.
 *
 * @author aeremenok Date: 02.08.2010 Time: 16:58:40
 * @see MetaData#getOrder(PropertyDescriptor)
 */
public class AddOrder
        extends MetaDataUser
        implements Function<PropertyDescriptor, OrderedPropertyDescriptor>
{
// --------------------------- CONSTRUCTORS ---------------------------

    public AddOrder( @Nonnull final MetaData metaData )
    {
        super( metaData );
    }

// ------------------------ INTERFACE METHODS ------------------------

// --------------------- Interface Function ---------------------

    public OrderedPropertyDescriptor apply( @Nonnull final PropertyDescriptor from )
    {
        final int order = toInt( metaData.getOrder( from ) );
        return new OrderedPropertyDescriptor( from, order );
    }

// -------------------------- OTHER METHODS --------------------------

    protected int toInt( @Nullable final Integer i )
    {
        if ( i == null )
        {
            return Integer.MAX_VALUE;
        }
        return i;
    }
}
