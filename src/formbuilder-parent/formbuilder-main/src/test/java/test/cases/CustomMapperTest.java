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
package test.cases;

import domain.Person;
import org.fest.swing.fixture.JPanelFixture;
import org.formbuilder.Form;
import org.formbuilder.FormBuilder;
import org.formbuilder.mapping.beanmapper.PropertyNameBeanMapper;
import org.formbuilder.mapping.beanmapper.PropertyNameContext;
import org.formbuilder.mapping.beanmapper.SampleBeanMapper;
import org.formbuilder.mapping.beanmapper.SampleContext;
import org.testng.annotations.Test;

import javax.swing.*;
import java.awt.*;

import static org.testng.Assert.fail;

/** @author aeremenok Date: 30.07.2010 Time: 16:22:16 */
public class CustomMapperTest
        extends FormTest
{
    @Test( dependsOnMethods = "customizedBySample" )
    public void customizedByPropertyName()
    {
        assert !SwingUtilities.isEventDispatchThread();
        final Form<Person> form = env.buildFormInEDT( FormBuilder.map( Person.class ).with( new PropertyNameBeanMapper<Person>()
        {
            @Override
            public JComponent mapBean( final PropertyNameContext<Person> ctx )
            {
                final JPanel panel = new JPanel( new BorderLayout() );
                panel.add( ctx.label( "name" ), BorderLayout.NORTH );
                panel.add( ctx.editor( "name" ), BorderLayout.CENTER );
                return panel;
            }
        } ) );

        final JComponent component = form.asComponent();
        env.verifyLayout( component, JPanel.class, BorderLayout.class );
        env.addToWindow( form );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();
        wrapperPanel.textBox( "name" );
        wrapperPanel.label( "name" );

        try
        {
            wrapperPanel.spinner( "age" );
            fail();
        }
        catch ( final Exception ignored )
        {}
    }

    @Test
    public void customizedBySample()
    {
        assert !SwingUtilities.isEventDispatchThread();
        final Form<Person> form = env.buildFormInEDT( FormBuilder.map( Person.class ).with( new SampleBeanMapper<Person>()
        {
            @Override
            public JComponent mapBean( final Person sample,
                                       final SampleContext<Person> context )
            {
                final JPanel panel = new JPanel( new BorderLayout() );
                panel.add( context.label( sample.getName() ), BorderLayout.NORTH );
                panel.add( context.editor( sample.getName() ), BorderLayout.CENTER );
                return panel;
            }
        } ) );

        final JComponent component = form.asComponent();
        env.verifyLayout( component, JPanel.class, BorderLayout.class );
        env.addToWindow( form );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();
        wrapperPanel.textBox( "name" );
        wrapperPanel.label( "name" );

        try
        {
            wrapperPanel.spinner( "age" );
            fail();
        }
        catch ( final Exception ignored )
        {}
    }
}
