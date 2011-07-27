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
import org.testng.annotations.Test;

import static org.testng.Assert.fail;

/** @author eav Date: 31.07.2010 Time: 20:48:24 */
public class HiddenTest
        extends FormTest
{
    @Test
    public void testHidden()
    {
        env.addDefaultForm( Person.class );
        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();
        try
        {
            wrapperPanel.spinner( "id" );
            fail();
        }
        catch ( final Exception ignored )
        { }
    }
}
