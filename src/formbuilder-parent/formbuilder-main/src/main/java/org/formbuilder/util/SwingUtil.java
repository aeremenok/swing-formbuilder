package org.formbuilder.util;

import com.google.common.base.Preconditions;

import javax.swing.*;

/**
 * An utility class providing common Swing methods
 *
 * @author eav Date: 02.03.11 Time: 14:59
 */
public class SwingUtil
{
    /**
     * Checks whether the current thread is Swing EDT. If not - throws an Exception with an advice
     *
     * @throws IllegalStateException when the current thread is not Swing EDT
     */
    public static void checkForEventDispatchThread() throws IllegalStateException
    {
        Preconditions.checkState( SwingUtilities.isEventDispatchThread(),
                                  "The method that caused this trace must be called in the Event Dispatch Thread, see http://java.sun.com/products/jfc/tsc/articles/threads/threads1.html" );
    }
}
