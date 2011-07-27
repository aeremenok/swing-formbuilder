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
import org.fest.swing.fixture.JTextComponentFixture;
import org.formbuilder.Form;
import org.formbuilder.FormBuilder;
import org.testng.annotations.Test;

import java.awt.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertNull;

/** @author aeremenok Date: 30.07.2010 Time: 16:21:04 */
public class ValidationTest
        extends FormTest
{
    @Test( dependsOnMethods = "testValidation" )
    public void disableValidation()
    {
        final Form<Person> form = env.buildFormInEDT( FormBuilder.map( Person.class ).doValidation( false ) );
        env.addToWindow( form.asComponent() );

        final Person oldValue = env.createPerson();
        env.setValueInEDT( form, oldValue );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();
        final JTextComponentFixture nameTextBox = wrapperPanel.textBox( "name" );

        assertNotSame( nameTextBox.target.getBackground(), Color.PINK );
        assertNull( nameTextBox.target.getToolTipText() );
        nameTextBox.setText( "ee" );
        assertNotSame( nameTextBox.target.getBackground(), Color.PINK );
        assertNull( nameTextBox.target.getToolTipText() );
    }

    @Test
    public void testValidation()
    {
        final Form<Person> form = env.buildFormInEDT( FormBuilder.map( Person.class ) );
        env.addToWindow( form.asComponent() );

        final Person oldValue = env.createPerson();
        env.setValueInEDT( form, oldValue );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();
        final JTextComponentFixture nameTextBox = wrapperPanel.textBox( "name" );

        assertNotSame( nameTextBox.target.getBackground(), Color.PINK );
        assertNull( nameTextBox.target.getToolTipText() );
        nameTextBox.setText( "12" );
        assertEquals( nameTextBox.target.getBackground(), Color.PINK );
        nameTextBox.requireToolTip( "size must be between 3 and 2147483647" );

        nameTextBox.setText( "123" );
        assertNotSame( nameTextBox.target.getBackground(), Color.PINK );
        assertNull( nameTextBox.target.getToolTipText() );
    }
}
