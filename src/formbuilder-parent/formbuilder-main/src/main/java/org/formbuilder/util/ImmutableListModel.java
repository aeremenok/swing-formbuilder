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

package org.formbuilder.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author eav Date: 22.08.2010 Time: 22:39:32
 * @param <T> item type
 */
public class ImmutableListModel<T>
        extends AbstractListModel
{
// ------------------------------ FIELDS ------------------------------
    private final List<T> data;

// --------------------------- CONSTRUCTORS ---------------------------

    public ImmutableListModel( @Nonnull final Collection<T> data )
    {
        if ( data instanceof List )
        {
            this.data = (List<T>) data;
        }
        else
        {
            this.data = new ArrayList<T>( data );
        }
    }

// ------------------------ INTERFACE METHODS ------------------------

// --------------------- Interface ListModel ---------------------

    public int getSize()
    {
        return data == null ? 0 : data.size();
    }

    public T getElementAt( final int index )
    {
        return data.get( index );
    }

// -------------------------- OTHER METHODS --------------------------

    public int indexOf( @Nullable final T o )
    {
        return data.indexOf( o );
    }
}
