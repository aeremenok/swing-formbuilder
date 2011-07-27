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
package org.formbuilder.mapping.metadata;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.beans.PropertyDescriptor;

/**
 * Reads property attributes from {@link UIManager#getDefaults()}. The attribute key looks this way:
 * "BeanType.propertyName.attribute". For example:
 * <p/>
 * <pre>
 * UIManager.getDefaults().put("Person.name.order", 1);
 * </pre>
 *
 * @author eav Date: 31.07.2010 Time: 12:18:28
 */
public class UIManagerMetaData
        implements MetaData
{
// ------------------------ INTERFACE METHODS ------------------------

// --------------------- Interface MetaData ---------------------

    @Nullable
    public Integer getOrder( @Nonnull final PropertyDescriptor descriptor )
    {
        final Object o = UIManager.get( getQName( descriptor ) + ".order" );
        if ( o == null )
        {
            return null;
        }
        if ( o instanceof Number )
        {
            final Number n = (Number) o;
            return n.intValue();
        }

        try
        {
            return Integer.valueOf( o.toString() );
        }
        catch ( final NumberFormatException e )
        {
            return null;
        }
    }

    @Nullable
    public String getTitle( @Nonnull final PropertyDescriptor descriptor )
    {
        return UIManager.getString( getQName( descriptor ) + ".title" );
    }

    public boolean isHidden( @Nonnull final PropertyDescriptor descriptor )
    {
        return UIManager.getBoolean( getQName( descriptor ) + ".hidden" );
    }

    public boolean isReadOnly( @Nonnull final PropertyDescriptor descriptor )
    {
        return UIManager.getBoolean( getQName( descriptor ) + ".readonly" );
    }

// -------------------------- OTHER METHODS --------------------------

    @Nonnull
    protected String getQName( final PropertyDescriptor descriptor )
    {
        final String className = descriptor.getReadMethod().getDeclaringClass().getSimpleName();
        final String propertyName = descriptor.getName();
        return className + "." + propertyName;
    }
}
