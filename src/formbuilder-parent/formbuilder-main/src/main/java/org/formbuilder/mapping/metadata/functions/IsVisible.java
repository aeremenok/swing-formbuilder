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
import org.formbuilder.mapping.metadata.MetaData;
import org.formbuilder.mapping.metadata.MetaDataUser;

import javax.annotation.Nonnull;
import java.beans.PropertyDescriptor;

/**
 * Returns true iff {@link MetaData#isHidden(PropertyDescriptor)} returns false.
 *
 * @author aeremenok Date: 02.08.2010 Time: 16:56:56
 */
public class IsVisible
        extends MetaDataUser
        implements Predicate<PropertyDescriptor>
{
    public IsVisible( @Nonnull final MetaData metaData )
    {
        super( metaData );
    }

    public boolean apply( @Nonnull final PropertyDescriptor input )
    {
        return !metaData.isHidden( input );
    }
}
