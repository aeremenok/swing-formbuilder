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
import org.formbuilder.mapping.typemapper.GetterConfig;
import org.formbuilder.mapping.typemapper.GetterMapper;
import org.formbuilder.mapping.typemapper.impl.StringMapper;
import org.testng.annotations.Test;

import javax.swing.*;

/** @author aeremenok Date: 30.07.2010 Time: 16:36:33 */
public class PropertyMappingTest
        extends FormTest
{
    @Test
    public void mapByGetter()
    {
        final FormBuilder<Person> formBuilder = FormBuilder.map( Person.class )
                .useForGetters( new GetterMapper<Person>()
                {
                    public void mapGetters( final Person beanSample,
                                            final GetterConfig config )
                    {
                        config.use( beanSample.getDescription(), new StringToTextAreaMapper() );
                    }
                } );
        final Form<Person> form = env.buildFormInEDT( formBuilder );
        env.addToWindow( form.asComponent() );

        env.setValueInEDT( form, env.createPerson() );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();

        final JTextComponentFixture nameComponent = wrapperPanel.textBox( "name" );
        assert nameComponent.target instanceof JTextField;

        final JTextComponentFixture descComponent = wrapperPanel.textBox( "description" );
        assert descComponent.target instanceof JTextArea;
    }

    @Test( dependsOnMethods = "mapByGetter" )
    public void mapByPropertyName()
    {
        final FormBuilder<Person> formBuilder = FormBuilder.map( Person.class )
                .useForProperty( "description", new StringToTextAreaMapper() );
        final Form<Person> form = env.buildFormInEDT( formBuilder );
        env.addToWindow( form.asComponent() );

        env.setValueInEDT( form, env.createPerson() );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();

        final JTextComponentFixture nameComponent = wrapperPanel.textBox( "name" );
        assert nameComponent.target instanceof JTextField;

        final JTextComponentFixture descComponent = wrapperPanel.textBox( "description" );
        assert descComponent.target instanceof JTextArea;
    }

    public static class StringToTextAreaMapper
            extends StringMapper<JTextArea>
    {
        public JTextArea createEditorComponent()
        {
            return new JTextArea();
        }
    }
}
