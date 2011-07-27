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
package test.env;

import javax.swing.*;
import java.awt.*;

/** @author eav 2009 */
public class WrapperPanel
        extends JPanel
{
    protected final JComponent component;

    public WrapperPanel( final JComponent component )
    {
        this( component, false );
    }

    public WrapperPanel( final JComponent component,
                         final boolean scroll )
    {
        super( new BorderLayout() );
        this.component = component;
        if ( scroll )
        {
            add( new JScrollPane( component ), BorderLayout.CENTER );
        }
        else
        {
            add( component, BorderLayout.CENTER );
        }
    }

    public JComponent unwrap()
    {
        return component;
    }
}
