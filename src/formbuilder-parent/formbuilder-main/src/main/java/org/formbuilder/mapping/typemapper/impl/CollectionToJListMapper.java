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
import org.formbuilder.util.ImmutableListModel;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Maps properties of type {@link Collection} and its subclasses to {@link JList}. Its value is changed by selecting the
 * list elements.
 *
 * @author aeremenok Date: 30.07.2010 Time: 14:08:34
 * @param <CT> collection type
 * @param <I> collection item type
 */
@NotThreadSafe
public abstract class CollectionToJListMapper<I, CT extends Collection>
        implements TypeMapper<JList, CT>
{
    public void handleChanges( @Nonnull final JList editorComponent,
                               @Nonnull final ChangeHandler changeHandler )
    {
        editorComponent.getSelectionModel().addListSelectionListener( new ListSelectionListener()
        {
            public void valueChanged( final ListSelectionEvent e )
            {
                if ( !e.getValueIsAdjusting() )
                {
                    changeHandler.onChange();
                }
            }
        } );
    }

    @Nonnull
    public JList createEditorComponent()
    {
        return new JList( new ImmutableListModel<I>( getSuitableData() ) );
    }

    @Nullable
    @SuppressWarnings( {"unchecked"} )
    public CT getValue( @Nonnull final JList editorComponent )
    {
        final Object[] vs = editorComponent.getSelectedValues();
        final List<I> selectedValueList = new ArrayList<I>( vs.length );
        for ( final Object v : vs )
        {
            selectedValueList.add( (I) v );
        }
        return refine( selectedValueList );
    }

    @SuppressWarnings( {"unchecked"} )
    public void setValue( @Nonnull final JList editorComponent,
                          @Nullable final CT value )
    {
        if ( value == null || value.isEmpty() )
        {
            editorComponent.clearSelection();
            return;
        }

        final ImmutableListModel<I> model = (ImmutableListModel<I>) editorComponent.getModel();

        final int[] ixs = new int[value.size()];
        int i = 0;
        for ( final Object r : value )
        {
            ixs[i] = model.indexOf( (I) r );
            i++;
        }

        editorComponent.setSelectedIndices( ixs );
    }

    /** @return data to pass into JList. From these entries a user will be able to select */
    @Nonnull
    protected abstract Collection<I> getSuitableData();

    /**
     * Convert a list of selected values to a collection of a proper type
     *
     * @param selectedValues selected value list
     * @return collection of a proper type
     */
    @Nullable
    protected abstract CT refine( @Nonnull List<I> selectedValues );
}
