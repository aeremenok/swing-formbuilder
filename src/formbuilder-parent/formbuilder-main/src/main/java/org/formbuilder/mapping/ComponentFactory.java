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

package org.formbuilder.mapping;

import org.formbuilder.TypeMapper;
import org.formbuilder.mapping.metadata.MetaData;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.beans.PropertyDescriptor;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Creates and adjusts new components.
 *
 * @author aeremenok Date: Aug 18, 2010 Time: 1:52:39 PM
 */
public class ComponentFactory
{
// ------------------------------ FIELDS ------------------------------
    protected final MetaData metaData;

// --------------------------- CONSTRUCTORS ---------------------------

    public ComponentFactory( @Nonnull final MetaData metaData )
    {
        this.metaData = metaData;
    }

// -------------------------- OTHER METHODS --------------------------

    @Nonnull
    public JComponent createEditor( @Nonnull final PropertyDescriptor descriptor,
                                    @Nonnull final TypeMapper mapper )
    {
        final JComponent editor = checkNotNull( mapper.createEditorComponent() );
        editor.setEnabled( isEditable( descriptor ) );
        editor.setName( descriptor.getName() );
        return editor;
    }

    protected boolean isEditable( @Nonnull final PropertyDescriptor descriptor )
    {
        final boolean hasWriteMethod = descriptor.getWriteMethod() != null;
        return hasWriteMethod && !metaData.isReadOnly( descriptor );
    }

    @Nonnull
    public JLabel createLabel( @Nonnull final PropertyDescriptor descriptor )
    {
        final JLabel label = new JLabel( metaData.getTitle( descriptor ) );
        label.setName( descriptor.getName() );
        return label;
    }
}
