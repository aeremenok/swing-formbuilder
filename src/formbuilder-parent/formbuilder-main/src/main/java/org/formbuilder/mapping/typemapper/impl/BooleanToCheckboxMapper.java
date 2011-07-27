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
package org.formbuilder.mapping.typemapper.impl;

import org.formbuilder.TypeMapper;
import org.formbuilder.mapping.change.ChangeHandler;
import org.formbuilder.validation.BackgroundMarker;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Maps properties of type {@link Boolean} to {@link JCheckBox}
 *
 * @author aeremenok Date: 30.07.2010 Time: 13:38:16
 */
@NotThreadSafe
public class BooleanToCheckboxMapper
        implements TypeMapper<JCheckBox, Boolean>
{
    public void handleChanges( @Nonnull final JCheckBox editorComponent,
                               @Nonnull final ChangeHandler changeHandler )
    {
        editorComponent.addChangeListener( new ChangeListener()
        {
            public void stateChanged( final ChangeEvent e )
            {
                changeHandler.onChange( BackgroundMarker.INSTANCE );
            }
        } );
    }

    @Nonnull
    public JCheckBox createEditorComponent()
    {
        return new JCheckBox();
    }

    public Boolean getValue( @Nonnull final JCheckBox editorComponent )
    {
        return editorComponent.isSelected();
    }

    @Nonnull
    public Class<Boolean> getValueClass()
    {
        return Boolean.class;
    }

    public void setValue( @Nonnull final JCheckBox editorComponent,
                          @Nullable final Boolean value )
    {
        editorComponent.setSelected( Boolean.TRUE.equals( value ) );
    }
}
