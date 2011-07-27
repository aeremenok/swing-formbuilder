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
package org.formbuilder.util;

import javax.swing.*;
import java.awt.*;

/**
 * Simplyfies the {@link GridBagLayout } usage
 *
 * @author eav 2009
 */
public class GridBagPanel
        extends JPanel
{
// ------------------------------ FIELDS ------------------------------
    protected final GridBagConstraints constraints = new GridBagConstraints();

// --------------------------- CONSTRUCTORS ---------------------------

    public GridBagPanel()
    {
        super( new GridBagLayout() );

        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.BASELINE;
        constraints.ipadx = 10;
        constraints.ipady = 0;
    }

// -------------------------- OTHER METHODS --------------------------

    public GridBagConstraints add( final JComponent component,
                                   final int row,
                                   final int col )
    {
        return add( component, row, col, 1, 1 );
    }

    public GridBagConstraints add( final JComponent component,
                                   final int row,
                                   final int col,
                                   final int weightx )
    {
        return add( component, row, col, 1, 1, weightx );
    }

    public GridBagConstraints add( final JComponent component,
                                   final int row,
                                   final int col,
                                   final int rowspan,
                                   final int colspan )
    {
        return add( component, row, col, rowspan, colspan, 1 );
    }

    public GridBagConstraints add( final JComponent component,
                                   final int row,
                                   final int col,
                                   final int rowspan,
                                   final int colspan,
                                   final int weightx )
    {
        constraints.gridx = col;
        constraints.gridy = row;
        constraints.gridwidth = colspan;
        constraints.gridheight = rowspan;
        constraints.weightx = weightx;
        constraints.weighty = 0;

        add( component, constraints );
        return constraints;
    }
}
