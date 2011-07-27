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

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * A bridge to CGLib dependency
 *
 * @author aeremenok Date: 09.08.2010 Time: 17:38:47
 */
public class CGLibUtil
{
// -------------------------- STATIC METHODS --------------------------

    @SuppressWarnings( {"unchecked"} )
    @Nonnull
    public static <T> T createCGLibProxy( final Class<T> beanClass,
                                          final InvocationHandler handler )
    {
        final net.sf.cglib.proxy.Enhancer e = new net.sf.cglib.proxy.Enhancer();
        e.setSuperclass( beanClass );
        e.setUseFactory( false );
        e.setCallback( new net.sf.cglib.proxy.InvocationHandler()
        {
            public Object invoke( final Object proxy,
                                  final Method method,
                                  final Object[] args )
                    throws
                    Throwable
            {
                return handler.invoke( proxy, method, args );
            }
        } );
        return (T) e.create();
    }
}
