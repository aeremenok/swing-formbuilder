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
package test.env;

import org.fest.swing.image.ScreenshotTaker;

import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/** @author aeremenok 2010 */
public class ScreenshotSaver
{
    private final ScreenshotTaker screenshotTaker = new ScreenshotTaker();
    private final SimpleDateFormat format = new SimpleDateFormat( "yyyy-dd-MM_hh-mm-ss" );

    public void saveDesktopScreenshot( final String screenshotDir )
    {
        new File( screenshotDir ).mkdir();
        screenshotTaker.saveDesktopAsPng( screenshotDir + "/" + newFileName() );
    }

    public void saveScreenshot( final Component component,
                                final String screenshotDir )
    {
        new File( screenshotDir ).mkdir();
        screenshotTaker.saveComponentAsPng( component, screenshotDir + "/" + newFileName() );
    }

    protected String newFileName()
    {
        return "scr" + format.format( new Date() ) + ".png";
    }
}
