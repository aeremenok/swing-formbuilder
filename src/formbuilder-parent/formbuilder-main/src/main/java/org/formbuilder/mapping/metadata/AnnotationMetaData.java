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

import org.formbuilder.annotations.UIHidden;
import org.formbuilder.annotations.UIOrder;
import org.formbuilder.annotations.UIReadOnly;
import org.formbuilder.annotations.UITitle;
import org.formbuilder.util.Reflection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.beans.PropertyDescriptor;

/**
 * Reads attributes of properties, which are specified using annotations
 *
 * @author eav Date: 31.07.2010 Time: 12:18:03
 */
public class AnnotationMetaData
        implements MetaData
{
// ------------------------ INTERFACE METHODS ------------------------

// --------------------- Interface MetaData ---------------------

    /**
     * {@inheritDoc}
     *
     * @see UIOrder
     */
    @Nullable
    public Integer getOrder( @Nonnull final PropertyDescriptor descriptor )
    {
        final UIOrder uiOrder = Reflection.getAnnotation( descriptor, UIOrder.class );
        if ( uiOrder != null )
        {
            return uiOrder.value();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see UITitle
     */
    @Nullable
    public String getTitle( @Nonnull final PropertyDescriptor descriptor )
    {
        final UITitle uiTitle = Reflection.getAnnotation( descriptor, UITitle.class );
        if ( uiTitle != null )
        {
            return uiTitle.value();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see UIHidden
     */
    public boolean isHidden( @Nonnull final PropertyDescriptor descriptor )
    {
        return Reflection.getAnnotation( descriptor, UIHidden.class ) != null;
    }

    /**
     * {@inheritDoc}
     *
     * @see UIReadOnly
     */
    public boolean isReadOnly( @Nonnull final PropertyDescriptor descriptor )
    {
        return Reflection.getAnnotation( descriptor, UIReadOnly.class ) != null;
    }
}
