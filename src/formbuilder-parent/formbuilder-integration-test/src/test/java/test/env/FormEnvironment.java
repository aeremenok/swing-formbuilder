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

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.formbuilder.Form;
import org.formbuilder.FormBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Callable;

/** @author aeremenok Date: 30.07.2010 Time: 16:08:40 */
public class FormEnvironment
        extends ComponentEnvironment<JPanel>
{
    public FormEnvironment()
    {
        super( new Callable<JPanel>()
        {
            public JPanel call()
            {
                return new JPanel( new BorderLayout() );
            }
        }, false );
    }

    public <B> Form<B> addDefaultForm( final Class<B> beanClass )
    {
        final Form<B> form = buildFormInEDT( FormBuilder.map( beanClass ) );

        final JComponent component = form.asComponent();
        verifyLayout( component, JPanel.class, GridBagLayout.class );
        addToWindow( form );
        return form;
    }

    public void addToWindow( final Form form )
    {
        addToWindow( form.asComponent() );
    }

    public void addToWindow( final JComponent component )
    {
        final JPanel panel = getComponent();
        panel.removeAll();
        panel.add( component );
        getFrameFixture().component().pack();
    }

    public <B> Form<B> buildFormInEDT( final FormBuilder<B> formBuilder )
    {
        return GuiActionRunner.execute( new GuiQuery<Form<B>>()
        {
            @Override
            protected Form<B> executeInEDT()
            {
                return formBuilder.buildForm();
            }
        } );
    }

    public <B> void setValueInEDT( final Form<B> form,
                                   final B value )
    {
        GuiActionRunner.execute( new GuiTask()
        {
            @Override
            protected void executeInEDT()
            {
                form.setValue( value );
            }
        } );
    }

    public void verifyLayout( final JComponent component,
                              final Class<? extends JComponent> superClass,
                              final Class<? extends LayoutManager> layoutClass )
    {
        assert superClass.isAssignableFrom( component.getClass() ) : component;
        assert layoutClass.isAssignableFrom( component.getLayout().getClass() ) : component.getLayout();
    }
}
