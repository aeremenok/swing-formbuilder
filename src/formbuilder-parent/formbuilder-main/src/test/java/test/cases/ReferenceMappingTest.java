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
import domain.Role;
import org.fest.swing.fixture.JComboBoxFixture;
import org.fest.swing.fixture.JPanelFixture;
import org.formbuilder.Form;
import org.formbuilder.FormBuilder;
import org.formbuilder.mapping.typemapper.impl.ReferenceToComboboxMapper;
import org.testng.annotations.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.testng.Assert.assertEquals;

/** @author aeremenok Date: 30.07.2010 Time: 16:17:38 */
public class ReferenceMappingTest
        extends FormTest
{
    @Test
    public void testReferenceEditor()
    {
        final Form<Person> form = env.buildFormInEDT( FormBuilder.map( Person.class ).use( new RoleMapper() ) );
        env.addToWindow( form );

        final Person oldValue = env.createPerson();
        env.setValueInEDT( form, oldValue );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();
        final JComboBoxFixture roleCombo = wrapperPanel.comboBox( "role" );
        roleCombo.requireItemCount( 2 );

        roleCombo.selectItem( 0 ).requireSelection( "admin" );
        assertEquals( form.getValue().getRole(), new Role( "admin" ) );

        roleCombo.selectItem( 1 ).requireSelection( "user" );
        assertEquals( form.getValue().getRole(), new Role( "user" ) );
    }

    private static class RoleMapper
            extends ReferenceToComboboxMapper<Role>
    {
        public Class<Role> getValueClass()
        {
            return Role.class;
        }

        @Override
        protected List<Role> getSuitableData()
        {
            return asList( new Role( "admin" ), new Role( "user" ) );
        }
    }
}
