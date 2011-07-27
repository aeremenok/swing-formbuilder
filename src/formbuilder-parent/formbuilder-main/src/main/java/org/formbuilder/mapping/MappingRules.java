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
package org.formbuilder.mapping;

import org.formbuilder.TypeMapper;
import org.formbuilder.mapping.exception.InvalidTypeMappingException;
import org.formbuilder.mapping.exception.UnmappedTypeException;
import org.formbuilder.mapping.typemapper.impl.BooleanToCheckboxMapper;
import org.formbuilder.mapping.typemapper.impl.DateToSpinnerMapper;
import org.formbuilder.mapping.typemapper.impl.NumberToSpinnerMapper;
import org.formbuilder.mapping.typemapper.impl.StringToTextFieldMapper;
import org.formbuilder.util.Reflection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * Detects which {@link TypeMapper} should be used for each property. todo make immutable
 *
 * @author aeremenok Date: 28.07.2010 Time: 11:39:46
 */
public class MappingRules
{
// ------------------------------ FIELDS ------------------------------
    protected final Map<Class, TypeMapper> typeToMapper = new HashMap<Class, TypeMapper>();
    protected final Map<String, TypeMapper> propertyNameToMapper = new HashMap<String, TypeMapper>();

// --------------------------- CONSTRUCTORS ---------------------------

    @SuppressWarnings( "unchecked" )
    public MappingRules()
    {
        this( asList( new StringToTextFieldMapper(),
                new NumberToSpinnerMapper(),
                new BooleanToCheckboxMapper(),
                new DateToSpinnerMapper() ) );
    }

    public MappingRules( final Iterable<? extends TypeMapper> mappers )
    {
        super();
        for ( final TypeMapper mapper : mappers )
        {
            addMapper( mapper );
        }
    }

    /**
     * Register a typemapper mapper for all properties of typemapper, specified by {@link TypeMapper#getValueClass()}.
     *
     * @param mapper typemapper mapper to register
     * @see MappingRules#addMapper(String, TypeMapper)
     * @see MappingRules#getMapper(PropertyDescriptor)
     */
    public void addMapper( @Nonnull final TypeMapper mapper )
    {
        typeToMapper.put( mapper.getValueClass(), mapper );
    }

// -------------------------- OTHER METHODS --------------------------

    /**
     * Register a typemapper mapper for a property with given name. Default mappers will remain for other properties of
     * the same typemapper.
     *
     * @param propertyName property name
     * @param mapper       typemapper mapper to register
     * @see MappingRules#addMapper(TypeMapper)
     * @see MappingRules#getMapper(PropertyDescriptor)
     */
    public void addMapper( @Nonnull final String propertyName,
                           @Nonnull final TypeMapper mapper )
    {
        // todo early call checkType
        propertyNameToMapper.put( propertyName, mapper );
    }

    /**
     * Pick a typemapper mapper for a given property.
     *
     * @param descriptor property introspection info
     * @return a mapper, which {@link TypeMapper#getValueClass()} is either the same as or the subclass of {@link
     *         PropertyDescriptor#getReadMethod()} typemapper
     *
     * @throws InvalidTypeMappingException found typemapper mapper, that returns wrong {@link TypeMapper#getValueClass()}
     * @throws UnmappedTypeException       no mappers found for a given property
     * @see MappingRules#addMapper(TypeMapper)
     * @see MappingRules#addMapper(String, TypeMapper)
     */
    @Nonnull
    public TypeMapper getMapper( @Nonnull final PropertyDescriptor descriptor )
            throws
            InvalidTypeMappingException,
            UnmappedTypeException
    {
        TypeMapper mapper = propertyNameToMapper.get( descriptor.getName() );
        if ( mapper != null )
        {
            return checkType( mapper, descriptor );
        }

        final Class<?> boxed = Reflection.box( descriptor.getPropertyType() );

        mapper = typeToMapper.get( boxed );
        if ( mapper != null )
        {
            return mapper;
        }

        final TypeMapper mapperOfSuperType = findMapperOfSuperType( boxed );
        if ( mapperOfSuperType != null )
        {
            return mapperOfSuperType;
        }

        throw new UnmappedTypeException( descriptor );
    }

    @Nonnull
    protected TypeMapper checkType( @Nonnull final TypeMapper mapper,
                                    @Nonnull final PropertyDescriptor descriptor )
            throws
            InvalidTypeMappingException
    {
        final Class<?> boxed = Reflection.box( descriptor.getPropertyType() );
        if ( !boxed.isAssignableFrom( mapper.getValueClass() ) )
        {
            throw new InvalidTypeMappingException( descriptor, mapper );
        }
        return mapper;
    }

    @SuppressWarnings( "unchecked" )
    @Nullable
    protected TypeMapper findMapperOfSuperType( @Nonnull final Class<?> subtype )
    {
        for ( final Map.Entry<Class, TypeMapper> entry : typeToMapper.entrySet() )
        {
            if ( entry.getKey().isAssignableFrom( subtype ) )
            {
                return entry.getValue();
            }
        }
        return null;
    }
}
