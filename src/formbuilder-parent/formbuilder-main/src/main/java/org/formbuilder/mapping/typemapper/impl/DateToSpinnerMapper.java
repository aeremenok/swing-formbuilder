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
import java.util.Date;

/**
 * Maps properties of type {@link Date} to {@link JSpinner} with {@link SpinnerDateModel}
 *
 * @author aeremenok Date: 28.07.2010 Time: 11:57:47
 */
@NotThreadSafe
public class DateToSpinnerMapper
        implements TypeMapper<JSpinner, Date>
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
        return new JSpinner( new SpinnerDateModel() );
    }

    @Nullable
    public Date getValue( @Nonnull final JSpinner editorComponent )
    {
        return (Date) editorComponent.getValue();
    }

    @Nonnull
    public Class<Date> getValueClass()
    {
        return Date.class;
    }

    public void setValue( @Nonnull final JSpinner editorComponent,
                          @Nullable Date value )
    {
        if ( value == null )
        {
            value = new Date( 0 );
        }
        editorComponent.setValue( value );
    }
}
