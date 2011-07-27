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

/**
 *
 */
package org.formbuilder;

import org.formbuilder.mapping.change.ChangeHandler;
import org.formbuilder.validation.ValidationMarker;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.*;

/**
 * Maps the values of a given typemapper to the given editors.
 *
 * @author aeremenok 2010
 * @param <C> editor component type
 * @param <V> value type
 */
@NotThreadSafe
public interface TypeMapper<C extends JComponent, V>
{
    /**
     * Propagates the changes of the editor component's state to the form. Particularly, this is needed to validate the
     * changes.
     *
     * @param editorComponent an editor, which state should be observed
     * @param changeHandler   you should call {@link ChangeHandler#onChange(ValidationMarker...)} when editorComponent
     *                        changes its state
     */
    void handleChanges( @Nonnull C editorComponent,
                        @Nonnull ChangeHandler changeHandler );

    /**
     * A factory method for editors.
     *
     * @return new instance of editor component
     */
    @Nonnull
    C createEditorComponent();

    /**
     * @param editorComponent an editor to extract its value
     * @return editor's value
     */
    @Nullable
    V getValue( @Nonnull C editorComponent );

    /** @return value class, which should be processed using this mapper */
    @Nonnull
    Class<V> getValueClass();

    /**
     * @param editorComponent an editor to display new value
     * @param value           a new property value
     */
    void setValue( @Nonnull C editorComponent,
                   @Nullable V value );
}
