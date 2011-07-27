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
package org.formbuilder.util;

import com.google.common.base.Predicate;
import org.formbuilder.mapping.exception.AccessorNotFoundException;
import org.formbuilder.mapping.exception.PropertyNotFoundException;
import org.formbuilder.mapping.metadata.functions.HasAccessor;
import org.formbuilder.mapping.metadata.functions.HasName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Iterators.find;
import static com.google.common.collect.Iterators.forArray;

/**
 * Common reflection operations
 *
 * @author aeremenok 2010
 */
public class Reflection
{
// ------------------------------ FIELDS ------------------------------
    private static final Map<Class, Object> emptyValues = new HashMap<Class, Object>();
    private static final Map<Class, Class> primitiveToBox = new HashMap<Class, Class>();

// -------------------------- STATIC METHODS --------------------------

    static
    {
        emptyValues.put( Integer.TYPE, Integer.MIN_VALUE );
        emptyValues.put( Long.TYPE, Long.MIN_VALUE );
        emptyValues.put( Short.TYPE, Short.MIN_VALUE );
        emptyValues.put( Character.TYPE, Character.MIN_VALUE );
        emptyValues.put( Double.TYPE, Double.MIN_VALUE );
        emptyValues.put( Float.TYPE, Float.MIN_VALUE );
        emptyValues.put( Boolean.TYPE, Boolean.FALSE );
    }

    static
    {
        primitiveToBox.put( Integer.TYPE, Integer.class );
        primitiveToBox.put( Long.TYPE, Long.class );

        primitiveToBox.put( Double.TYPE, Double.class );
        primitiveToBox.put( Float.TYPE, Float.class );

        primitiveToBox.put( Boolean.TYPE, Boolean.class );

        primitiveToBox.put( Character.TYPE, Character.class );
        primitiveToBox.put( Short.TYPE, Short.class );

        primitiveToBox.put( Void.TYPE, Void.class );
    }

    /**
     * @param mayBePrimitive a class to do the boxing
     * @return a wrapper class, if the class is primitive, mayBePrimitive otherwise
     */
    @Nonnull
    public static Class<?> box( @Nonnull final Class<?> mayBePrimitive )
    {
        if ( !mayBePrimitive.isPrimitive() )
        {
            return mayBePrimitive;
        }

        final Class boxed = primitiveToBox.get( mayBePrimitive );
        return checkNotNull( boxed, "Cannot box primitive typemapper " + mayBePrimitive );
    }

    /**
     * @param beanClass a class to make new proxy
     * @param handler   a method interceptor
     * @param <T>       beanmapper typemapper
     * @return a proxy, that uses a given handler
     */
    @Nonnull
    public static <T> T createProxy( @Nonnull final Class<T> beanClass,
                                     @Nonnull final InvocationHandler handler )
    {
        if ( beanClass.isInterface() )
        {
            return createNativeProxy( beanClass, handler );
        }
        return CGLibUtil.createCGLibProxy( beanClass, handler );
    }

    /**
     * Finds a {@link PropertyDescriptor} by a getter method
     *
     * @param accessor     an accessor method
     * @param isReadMethod true if an accessor is a getter method, false if setter
     * @return property descriptor
     * @throws AccessorNotFoundException no property with such getter
     * @see PropertyDescriptor#getReadMethod()
     */
    @Nonnull
    public static PropertyDescriptor getDescriptor( @Nonnull final Method accessor,
                                                    boolean isReadMethod )
            throws
            AccessorNotFoundException
    {
        try
        {
            return getDescpriptor( accessor.getDeclaringClass(), new HasAccessor( accessor, isReadMethod ) );
        }
        catch ( final NoSuchElementException e )
        {
            throw new AccessorNotFoundException( accessor );
        }
    }

    protected static PropertyDescriptor getDescpriptor( @Nonnull final Class beanClass,
                                                        @Nonnull final Predicate<PropertyDescriptor> predicate )
            throws
            NoSuchElementException
    {// todo use cache
        final BeanInfo beanInfo = getBeanInfo( beanClass );
        return find( forArray( beanInfo.getPropertyDescriptors() ), predicate );
    }

    /**
     * Swallow {@link IntrospectionException}s on {@link Introspector#getBeanInfo(Class)} call
     *
     * @param beanClass introspection source
     * @return bean info
     */
    @Nonnull
    public static BeanInfo getBeanInfo( @Nonnull final Class<?> beanClass )
    {
        try
        {
            return Introspector.getBeanInfo( beanClass );
        }
        catch ( final IntrospectionException e )
        {
            throw new RuntimeException( e );
        }
    }

    /**
     * Finds a {@link PropertyDescriptor} by a property name
     *
     * @param beanClass    where to search
     * @param propertyName a name of property
     * @return property descriptor
     * @throws PropertyNotFoundException no property with such name
     * @see PropertyDescriptor#getName()
     */
    @Nonnull
    public static PropertyDescriptor getDescriptor( @Nonnull final Class beanClass,
                                                    @Nonnull final String propertyName )
            throws
            PropertyNotFoundException
    {
        try
        {
            return getDescpriptor( beanClass, new HasName( propertyName ) );
        }
        catch ( final NoSuchElementException e )
        {
            throw new PropertyNotFoundException( beanClass, propertyName );
        }
    }

    /**
     * Get a value of a given property of a given beanmapper
     *
     * @param descriptor property descriptor
     * @param bean       value holder
     * @return a property value or an empty value for this property typemapper if beanmapper is null
     * @see Reflection#emptyValue(Method)
     * @see Reflection#setValue(Object, Object, PropertyDescriptor)
     */
    @Nullable
    public static Object getValue( @Nonnull final PropertyDescriptor descriptor,
                                   @Nullable final Object bean )
    {
        if ( bean == null )
        {
            return emptyValue( descriptor.getReadMethod() );
        }

        try
        {
            final Method readMethod = descriptor.getReadMethod();
            // todo revert finally
            readMethod.setAccessible( true );
            return readMethod.invoke( bean );
        }
        catch ( final Exception e )
        {
            throw new RuntimeException( e );
        }
    }

    @Nonnull
    private static <T> T createProxyBean( @Nonnull final Class<T> beanInterface )
    {
        return createNativeProxy( beanInterface, new BeanLikeHandler() );
    }

    @SuppressWarnings( {"unchecked"} )
    @Nonnull
    private static <T> T createNativeProxy( @Nonnull final Class<T> beanInterface,
                                           @Nonnull final InvocationHandler handler )
    {
        return (T) Proxy.newProxyInstance( beanInterface.getClassLoader(), new Class[]{beanInterface}, handler );
    }

    /**
     * Pick a meaningless value to fake a getter call
     *
     * @param method a getter method
     * @return null if the method should return a reference, a constant otherwise
     */
    @Nullable
    public static Object emptyValue( @Nonnull final Method method )
    {
        final Class<?> returnType = method.getReturnType();
        if ( !returnType.isPrimitive() )
        {
            return null;
        }
        return checkNotNull( emptyValues.get( returnType ), "No default return value for method " + method );
    }

    /**
     * Swallow reflection exceptions
     *
     * @param aClass beanmapper class
     * @param <T>    beanmapper typemapper
     * @return a newly allocated instance of given typemapper
     * @see Class#newInstance()
     */
    @Nonnull
    public static <T> T newInstance( @Nonnull final Class<T> aClass )
    {
        if ( aClass.isInterface() )
        {
            return createProxyBean( aClass );
        }
        try
        {
            return aClass.newInstance();
        }
        catch ( final Exception e )
        {
            throw new RuntimeException( e );
        }
    }

    /**
     * Set a value of a given property of a given beanmapper
     *
     * @param bean               value holder
     * @param propertyValue      a value to set
     * @param propertyDescriptor a property to modify
     * @see Reflection#getValue(PropertyDescriptor, Object)
     */
    public static void setValue( @Nonnull final Object bean,
                                 @Nullable final Object propertyValue,
                                 @Nonnull final PropertyDescriptor propertyDescriptor )
    {
        try
        {
            final Method writeMethod = propertyDescriptor.getWriteMethod();
            // todo revert finally
            writeMethod.setAccessible( true );
            writeMethod.invoke( checkNotNull( bean ), propertyValue );
        }
        catch ( final Exception e )
        {
            throw new RuntimeException( e );
        }
    }

    /**
     * Retrieves an annotation from a property getter or a property field
     *
     * @param descriptor a property
     * @param annotationClass desired annotation class
     * @param <T> annotation type
     * @return annotation or null if it cannot be found
     */
    @Nullable
    public static <T extends Annotation> T getAnnotation( @Nonnull final PropertyDescriptor descriptor,
                                                          @Nonnull final Class<T> annotationClass )
    {
        final Method getter = descriptor.getReadMethod();
        final T getterAnnotation = getter.getAnnotation( annotationClass );
        if ( getterAnnotation != null )
        {
            return getterAnnotation;
        }

        try
        {
            final Class<?> declaringClass = getter.getDeclaringClass();
            final Field field = declaringClass.getDeclaredField( descriptor.getName() );
            return field.getAnnotation( annotationClass );
        }
        catch ( NoSuchFieldException e )
        {
            return null;
        }
    }
}
