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
package org.formbuilder.mapping.beanmapper;

import org.formbuilder.BeanMapper;
import org.formbuilder.mapping.BeanMappingContext;
import org.formbuilder.mapping.exception.MappingException;
import org.formbuilder.mapping.metadata.MetaData;
import org.formbuilder.mapping.metadata.sort.OrderedPropertyDescriptor;
import org.formbuilder.util.GridBagPanel;
import org.formbuilder.util.SwingUtil;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyDescriptor;
import java.util.List;

/**
 * Creates a {@link JPanel} with {@link GridBagLayout}. Each property label and editor are located in separate row in
 * order, specified by "order" attribute. For example, if orders aren't specified, properties are sorted by names:<br>
 * <table><tr><td> Age </td><td> 24 </td></tr><tr><td> Id </td><td> eav </td></tr></tr><tr><td> Smoker </td><td> false
 * </td></tr></table>
 *
 * @author aeremenok 2010
 * @param <B> bean type
 * @see MetaData#getOrder(PropertyDescriptor)
 */
@NotThreadSafe
public class GridBagMapper<B>
        implements BeanMapper<B>
{
// ------------------------ INTERFACE METHODS ------------------------

// --------------------- Interface BeanMapper ---------------------

    @Nonnull
    public JComponent map( @Nonnull final BeanMappingContext<B> context )
    {
        SwingUtil.checkForEventDispatchThread();

        final GridBagPanel gridBagPanel = new GridBagPanel();

        int row = 0;
        final List<OrderedPropertyDescriptor> sorted = context.getActiveSortedDescriptors();
        for ( final OrderedPropertyDescriptor orderedPropertyDescriptor : sorted )
        {
            final PropertyDescriptor descriptor = orderedPropertyDescriptor.getDescriptor();
            try
            {
                final JComponent editor = context.getEditor( descriptor );
                final JLabel label = context.getLabel( descriptor );

                gridBagPanel.add( editor, row, 1 );
                gridBagPanel.add( label, row, 0 );

                row++;
            }
            catch ( final MappingException e )
            {
                handleMappingException( e );
            }
        }

        return gridBagPanel;
    }

// -------------------------- OTHER METHODS --------------------------

    protected void handleMappingException( @SuppressWarnings( "unused" ) @Nonnull final MappingException e )
    {
        // skip wrong property
    }
}
