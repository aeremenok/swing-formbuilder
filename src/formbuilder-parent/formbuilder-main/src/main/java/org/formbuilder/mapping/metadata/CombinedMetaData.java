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
import java.beans.PropertyDescriptor;

import static org.formbuilder.util.TextUtil.capitalize;

/**
 * Performs the attribute lookup in UIManagerMetaData, then in AnnotationMetaData, if the attibute cannot be found.
 *
 * @author eav Date: 31.07.2010 Time: 17:04:02
 */
public class CombinedMetaData
        implements MetaData
{
// ------------------------------ FIELDS ------------------------------
    private final AnnotationMetaData annotationMetaData = new AnnotationMetaData();
    private final UIManagerMetaData uiManagerMetaData = new UIManagerMetaData();

// ------------------------ INTERFACE METHODS ------------------------

// --------------------- Interface MetaData ---------------------

    @Nullable
    public Integer getOrder( @Nonnull final PropertyDescriptor descriptor )
    {
        return nvl( uiManagerMetaData.getOrder( descriptor ), annotationMetaData.getOrder( descriptor ) );
    }

    @Nonnull
    public String getTitle( @Nonnull final PropertyDescriptor descriptor )
    {
        return nvl( uiManagerMetaData.getTitle( descriptor ),
                annotationMetaData.getTitle( descriptor ),
                capitalize( descriptor.getDisplayName() ) );
    }

    public boolean isHidden( @Nonnull final PropertyDescriptor descriptor )
    {
        return uiManagerMetaData.isHidden( descriptor ) || annotationMetaData.isHidden( descriptor );
    }

    public boolean isReadOnly( @Nonnull final PropertyDescriptor descriptor )
    {
        return uiManagerMetaData.isReadOnly( descriptor ) || annotationMetaData.isReadOnly( descriptor );
    }

// -------------------------- OTHER METHODS --------------------------

    @Nullable
    private <T> T nvl( final T... t )
    { // todo defer calls
        for ( final T t0 : t )
        {
            if ( t0 != null )
            {
                return t0;
            }
        }
        return null;
    }
}
