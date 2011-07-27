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

/**
 * Provides attributes of bean properties.
 *
 * @author eav Date: 31.07.2010 Time: 12:17:38
 */
public interface MetaData
{
    /**
     * @param descriptor a property
     * @return position among another properties, where this property appears when the form is created
     */
    @Nullable
    Integer getOrder( @Nonnull PropertyDescriptor descriptor );

    /**
     * @param descriptor a property
     * @return a title, which will be displayed by JLabel for this property
     */
    @Nullable
    String getTitle( @Nonnull PropertyDescriptor descriptor );

    /**
     * @param descriptor a property
     * @return true if this property should not be displayed
     */
    boolean isHidden( @Nonnull PropertyDescriptor descriptor );

    /**
     * @param descriptor a property
     * @return true if this property should not be modified
     */
    boolean isReadOnly( @Nonnull PropertyDescriptor descriptor );
}
