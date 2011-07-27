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
package test.cases;

import domain.Person;
import org.fest.swing.fixture.JPanelFixture;
import org.formbuilder.Form;
import org.formbuilder.FormBuilder;
import org.testng.annotations.Test;

import javax.swing.*;
import java.awt.*;

import static org.testng.Assert.assertEquals;

/** @author aeremenok 2010 */
public class DefaultBuilderTest
        extends FormTest
{
    @Test
    public void setAndGetValue()
    {
        final Form<Person> form = env.buildFormInEDT( FormBuilder.map( Person.class ) );

        final JComponent component = form.asComponent();
        env.verifyLayout( component, JPanel.class, GridBagLayout.class );
        env.addToWindow( form );

        env.setValueInEDT( form, null );
        assert form.getValue() != null;

        final Person person = env.createPerson();
        env.setValueInEDT( form, person );
        requireNewBeanCreated( form, person );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();

        wrapperPanel.label( "name" ).requireText( "Person's first name" );
        wrapperPanel.label( "age" ).requireText( "Age" );
        wrapperPanel.label( "birthDate" ).requireText( "Date of birth" );
        wrapperPanel.label( "gender" ).requireText( "Gender" );

        wrapperPanel.textBox( "name" ).requireText( person.getName() );
        wrapperPanel.spinner( "age" ).requireValue( person.getAge() );
        wrapperPanel.spinner( "birthDate" ).requireValue( person.getBirthDate() );
        wrapperPanel.checkBox( "gender" ).requireSelected();

        screenshotSaver.saveScreenshot( env.getWrapperPanelFixture().target,
                System.getProperty( "java.io.tmpdir" ) + "/sfb" );
    }

    private void requireNewBeanCreated( final Form<Person> form,
                                        final Person oldValue )
    {
        final Person newValue = form.getValue();
        assertEquals( newValue, oldValue );
        assert form.getValue() != oldValue;
    }
}
