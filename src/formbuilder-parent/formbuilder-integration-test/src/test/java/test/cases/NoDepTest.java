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

import domain.Address;
import org.fest.swing.fixture.JPanelFixture;
import org.formbuilder.Form;
import org.formbuilder.FormBuilder;
import org.formbuilder.mapping.beanmapper.SampleBeanMapper;
import org.formbuilder.mapping.beanmapper.SampleContext;
import org.testng.annotations.Test;

import javax.annotation.Nonnull;
import javax.swing.*;

import static org.testng.Assert.fail;

/** @author aeremenok Date: 09.08.2010 Time: 17:32:06 */
public class NoDepTest
        extends FormTest
{
    @Test
    public void testMinimalConfig()
    {
        final FormBuilder<Address> b = FormBuilder.map( Address.class ).doValidation( false );
        final Form<Address> form = env.buildFormInEDT( b );
        env.addToWindow( form );

        final JPanelFixture mainPanel = env.getWrapperPanelFixture();
        mainPanel.textBox( "country" ).enterText( "Russia" );
        mainPanel.textBox( "city" ).enterText( "Saint-Petersburg" );
    }

    @Test( dependsOnMethods = "tryValidation" )
    public void tryCgLib()
    {
        final FormBuilder<Address> b = FormBuilder.map( Address.class ).with( new SampleBeanMapper<Address>()
        {
            @Override
            protected JComponent mapBean( @Nonnull final Address sample,
                                          final SampleContext<Address> context )
            {
                final Box box = Box.createHorizontalBox();
                box.add( context.editor( sample.getCity() ) );
                return box;
            }
        } );

        try
        {
            env.buildFormInEDT( b );
            fail();
        }
        catch ( final Throwable e )
        {
            log.error( e.getMessage(), e );
        }
    }

    @Test( dependsOnMethods = "testMinimalConfig" )
    public void tryValidation()
    {
        final FormBuilder<Address> b = FormBuilder.map( Address.class ).doValidation( true );
        try
        {
            env.buildFormInEDT( b );
            fail();
        }
        catch ( final Throwable e )
        {
            log.error( e.getMessage(), e );
        }
    }
}
