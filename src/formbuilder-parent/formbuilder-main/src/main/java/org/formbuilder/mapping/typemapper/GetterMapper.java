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
package org.formbuilder.mapping.typemapper;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

/**
 * Should be implemented to register type mappers by calling getters on a sample bean instance
 *
 * @author aeremenok Date: 30.07.2010 Time: 17:54:35
 * @param <B> bean type
 */
@NotThreadSafe
public interface GetterMapper<B>
{
    /**
     * @param beanSample a cglib-proxy, which records getter invocations
     * @param config     a type mapper registry frot-end
     */
    void mapGetters( @Nonnull final B beanSample,
                     @Nonnull final GetterConfig config );
}
