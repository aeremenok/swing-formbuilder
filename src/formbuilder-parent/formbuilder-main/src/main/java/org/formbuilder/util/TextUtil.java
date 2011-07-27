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
package org.formbuilder.util;

import com.google.common.base.Function;
import com.google.common.base.Joiner;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.ConstraintViolation;

import static com.google.common.collect.Iterables.transform;

/**
 * Common text operations
 *
 * @author aeremenok Date: 28.07.2010 Time: 13:29:57
 */
public class TextUtil
{
// -------------------------- STATIC METHODS --------------------------

    /**
     * @param s any string
     * @return given string, started with capital letter
     */
    public static String capitalize( @Nullable final String s )
    {
        if ( s == null )
        {
            return null;
        }
        if ( s.equals( "" ) )
        {
            return "";
        }
        return s.substring( 0, 1 ).toUpperCase() + s.substring( 1 );
    }

    /**
     * Converts a chain of constraint violations to a single string, using a given separator
     *
     * @param delimiter  a separator to use
     * @param violations validation results
     * @param <B>        beanmapper typemapper
     * @return violation messages, separated with delimiter
     *
     * @see ToMessage
     */
    @Nonnull
    public static <B> String digest( @Nonnull final String delimiter,
                                     @Nonnull final Iterable<ConstraintViolation<B>> violations )
    {
        return Joiner.on( delimiter ).join( transform( violations, ToMessage.F ) );
    }

// -------------------------- ENUMERATIONS --------------------------

    /** Returns a {@link ConstraintViolation#getMessage()} or "" if argument is null */
    public static enum ToMessage
            implements Function<ConstraintViolation, String>
    {
        F;

// ------------------------ INTERFACE METHODS ------------------------

// --------------------- Interface Function ---------------------

        public String apply( @Nullable final ConstraintViolation violation )
        {
            return violation == null ? "" : violation.getMessage();
        }
    }
}
