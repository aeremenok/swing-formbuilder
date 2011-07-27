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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import test.env.FormEnvironment;
import test.env.ScreenshotSaver;

/** @author eav Date: 31.07.2010 Time: 20:53:42 */
public abstract class FormTest
{
    protected final Logger log = LoggerFactory.getLogger( getClass() );
    protected final ScreenshotSaver screenshotSaver = new ScreenshotSaver();
    protected FormEnvironment env;

    @BeforeClass
    public void setUp()
            throws
            Exception
    {
        env = new FormEnvironment();
        env.setUp( this );
    }

    @AfterClass
    public void tearDown()
            throws
            Exception
    {
        env.tearDown( this );
    }
}
