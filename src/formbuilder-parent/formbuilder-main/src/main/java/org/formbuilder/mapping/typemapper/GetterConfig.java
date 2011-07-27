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

import org.formbuilder.TypeMapper;
import org.formbuilder.mapping.MappingRules;
import org.formbuilder.mapping.exception.AccessorNotFoundException;
import org.formbuilder.mapping.exception.NoGetterProvidedException;
import org.formbuilder.util.MethodRecorder;
import org.formbuilder.util.Reflection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.beans.PropertyDescriptor;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Registers type mappers for a properties, identified by calling getters.
 *
 * @author eav Date: 22.08.2010 Time: 21:35:24
 */
public class GetterConfig
{
// ------------------------------ FIELDS ------------------------------
    private final MappingRules mappingRules;
    private final MethodRecorder methodRecorder;

// --------------------------- CONSTRUCTORS ---------------------------

    public GetterConfig( @Nonnull final MappingRules mappingRules,
                         @Nonnull final MethodRecorder methodRecorder )
    {
        this.mappingRules = mappingRules;
        this.methodRecorder = methodRecorder;
    }

// -------------------------- OTHER METHODS --------------------------

    /**
     * @param whatProxyGetterReturned the result of calling the proxy getter, which is actually ignored. Instead the
     *                                getter call is recorded by a cglib proxy to determine the property.
     * @param mapper                  will be used for a property, defined by a getter call
     * @param <T>                     property type
     * @return this config
     * @throws NoGetterProvidedException no method was called on a sample bean before calling this injection method
     * @throws AccessorNotFoundException   the method, which was called on a sample bean is not a read method for any
     *                                   property
     */
    public <T> GetterConfig use( @SuppressWarnings( "unused" ) @Nullable final T whatProxyGetterReturned,
                                 @Nonnull final TypeMapper<?, ? extends T> mapper )
            throws
            NoGetterProvidedException,
            AccessorNotFoundException
    {
        if ( methodRecorder.getLastCalledMethod() == null )
        {
            throw new NoGetterProvidedException();
        }

        final PropertyDescriptor descriptor = Reflection.getDescriptor( methodRecorder.getLastCalledMethod(), true );
        mappingRules.addMapper( descriptor.getName(), checkNotNull( mapper ) );
        methodRecorder.reset();

        return this;
    }
}
