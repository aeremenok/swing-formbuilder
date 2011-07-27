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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

/**
 * Maps properties of type {@link String} to subclasses of {@link JTextComponent}
 *
 * @author eav Date: Aug 2, 2010 Time: 11:54:19 PM
 * @param <C> editor component type
 */
@NotThreadSafe
public abstract class StringMapper<C extends JTextComponent>
        implements TypeMapper<C, String>
{
    public void handleChanges( @Nonnull final C editorComponent,
                               @Nonnull final ChangeHandler changeHandler )
    {
        editorComponent.getDocument().addDocumentListener( new DocumentListener()
        {
            public void changedUpdate( final DocumentEvent e )
            {
                changeHandler.onChange( BackgroundMarker.INSTANCE );
            }

            public void insertUpdate( final DocumentEvent e )
            {
                changeHandler.onChange( BackgroundMarker.INSTANCE );
            }

            public void removeUpdate( final DocumentEvent e )
            {
                changeHandler.onChange( BackgroundMarker.INSTANCE );
            }
        } );
    }

    public String getValue( @Nonnull final C editorComponent )
    {
        final String text = editorComponent.getText();
        return "".equals( text ) ? null : text;
    }

    public Class<String> getValueClass()
    {
        return String.class;
    }

    public void setValue( @Nonnull final C editorComponent,
                          @Nullable final String value )
    {
        editorComponent.setText( value );
    }
}
