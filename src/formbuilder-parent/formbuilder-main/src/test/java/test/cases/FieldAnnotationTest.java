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

import org.fest.swing.fixture.JPanelFixture;
import org.fest.swing.fixture.JTextComponentFixture;
import org.formbuilder.Form;
import org.formbuilder.FormBuilder;
import org.formbuilder.annotations.UIHidden;
import org.formbuilder.annotations.UIOrder;
import org.formbuilder.annotations.UIReadOnly;
import org.formbuilder.annotations.UITitle;
import org.testng.annotations.Test;

import java.beans.IntrospectionException;

import static org.testng.Assert.fail;

/** @author aeremenok Date: 31.08.2010 Time: 11:08:28 */
public class FieldAnnotationTest
        extends FormTest
{
    @Test
    public void mapFields()
            throws
            IntrospectionException
    {
        final Form<WithFields> form = env.buildFormInEDT( FormBuilder.map( WithFields.class ) );
        env.addToWindow( form );

        final WithFields value = new WithFields();
        value.secondName = "john smith";
        value.id = 777;

        env.setValueInEDT( form, value );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();

        wrapperPanel.label( "secondName" ).requireText( "Test secondName" );
        try
        {
            wrapperPanel.label( "id" );
            fail();
        }
        catch ( Exception ignored )
        { }

        final JTextComponentFixture secondNameTextBox = wrapperPanel.textBox( "secondName" );
        secondNameTextBox.requireText( "john smith" );
        secondNameTextBox.requireDisabled();

        final JTextComponentFixture fisrtNameTextBox = wrapperPanel.textBox( "firstName" );
        assert getY( secondNameTextBox ) < getY( fisrtNameTextBox );
    }

    private int getY( final JTextComponentFixture fixture )
    {
        return fixture.target.getLocationOnScreen().y;
    }

    static class WithFields
    {
        @UITitle( "Test secondName" )
        @UIOrder( 0 )
        @UIReadOnly
        private String secondName;
        @UIOrder( 1 )
        private String firstName;
        @UIHidden
        private int id;

        public String getSecondName()
        {
            return secondName;
        }

        public void setSecondName( final String secondName )
        {
            this.secondName = secondName;
        }

        public int getId()
        {
            return id;
        }

        public void setId( final int id )
        {
            this.id = id;
        }

        public String getFirstName()
        {
            return firstName;
        }

        public void setFirstName( final String firstName )
        {
            this.firstName = firstName;
        }
    }
}
