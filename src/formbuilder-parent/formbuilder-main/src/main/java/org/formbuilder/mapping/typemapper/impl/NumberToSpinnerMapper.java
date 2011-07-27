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
 * Maps properties of type {@link Number} and its subtypes to {@link JSpinner} with {@link SpinnerNumberModel}
 *
 * @author aeremenok Date: 28.07.2010 Time: 11:55:54
 */
@NotThreadSafe
public class NumberToSpinnerMapper
        implements TypeMapper<JSpinner, Number>
{
    public void handleChanges( @Nonnull final JSpinner editorComponent,
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
    public JSpinner createEditorComponent()
    {
        return new JSpinner( new SpinnerNumberModel() );
    }

    @Nullable
    public Number getValue( @Nonnull final JSpinner editorComponent )
    {
        return (Number) editorComponent.getValue();
    }

    @Nonnull
    public Class<Number> getValueClass()
    {
        return Number.class;
    }

    public void setValue( @Nonnull final JSpinner editorComponent,
                          @Nullable Number value )
    {
        if ( value == null )
        {
            value = 0;
        }
        editorComponent.setValue( value );
    }
}
