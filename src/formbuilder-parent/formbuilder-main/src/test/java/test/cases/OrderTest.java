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
import org.fest.swing.fixture.JSpinnerFixture;
import org.fest.swing.fixture.JTextComponentFixture;
import org.testng.annotations.Test;

import javax.swing.*;

/** @author eav Date: 31.07.2010 Time: 21:05:55 */
public class OrderTest
        extends FormTest
{
    @Test( dependsOnMethods = "naturalOrder" )
    public void customOrder()
    {
        // unspecified order = maxint, so this should be handled normally
//        UIManager.getDefaults().put( "Person.age.order", 3 );
        UIManager.getDefaults().put( "Person.birthDate.order", 2 );
        UIManager.getDefaults().put( "Person.description.order", 1 );
        UIManager.getDefaults().put( "Person.name.order", 0 );

        env.addDefaultForm( Person.class );
        final JPanelFixture wrapper = env.getWrapperPanelFixture();

        final JSpinnerFixture ageSpinner = wrapper.spinner( "age" );
        final JSpinnerFixture birthDateSpinner = wrapper.spinner( "birthDate" );
        final JTextComponentFixture descTextBox = wrapper.textBox( "description" );
        final JTextComponentFixture nameTextBox = wrapper.textBox( "name" );

        assert getY( ageSpinner ) > getY( birthDateSpinner );
        assert getY( birthDateSpinner ) > getY( descTextBox );
        assert getY( descTextBox ) > getY( nameTextBox );

        UIManager.getDefaults().remove( "Person.age.order" );
        UIManager.getDefaults().remove( "Person.birthDate.order" );
        UIManager.getDefaults().remove( "Person.description.order" );
        UIManager.getDefaults().remove( "Person.name.order" );
    }

    @Test
    public void naturalOrder()
    {
        env.addDefaultForm( Person.class );
        final JPanelFixture wrapper = env.getWrapperPanelFixture();

        final JSpinnerFixture ageSpinner = wrapper.spinner( "age" );
        final JSpinnerFixture birthDateSpinner = wrapper.spinner( "birthDate" );
        final JTextComponentFixture descTextBox = wrapper.textBox( "description" );
        final JTextComponentFixture nameTextBox = wrapper.textBox( "name" );

        assert getY( ageSpinner ) < getY( birthDateSpinner );
        assert getY( birthDateSpinner ) < getY( descTextBox );
        assert getY( descTextBox ) < getY( nameTextBox );
    }

    private int getY( final JSpinnerFixture fixture )
    {
        return fixture.target.getLocationOnScreen().y;
    }

    private int getY( final JTextComponentFixture fixture )
    {
        return fixture.target.getLocationOnScreen().y;
    }
}
