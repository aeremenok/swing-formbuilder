package org.formbuilder.util;

import org.formbuilder.mapping.exception.AccessorNotFoundException;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles method  invocations as invocations of getters and setters of bean properties
 *
 * @author eav Date: 04.09.2010 Time: 18:58:28
 */
public class BeanLikeHandler
        implements InvocationHandler
{
    private Map<String, Object> propertyValues = new HashMap<String, Object>();

    public Object invoke( final Object proxy,
                          final Method method,
                          final Object[] args )
            throws
            Throwable
    { // todo optimize
        try
        {
            final PropertyDescriptor descriptor = Reflection.getDescriptor( method, true );
            return getValue( descriptor );
        }
        catch ( AccessorNotFoundException e )
        {
            final PropertyDescriptor descriptor = Reflection.getDescriptor( method, false );
            return setValue( descriptor, args );
        }
    }

    private Object getValue( final PropertyDescriptor descriptor )
    {
        final Object propertyValue = propertyValues.get( descriptor.getName() );
        if ( propertyValue != null )
        {
            return propertyValue;
        }
        return Reflection.emptyValue( descriptor.getReadMethod() );
    }

    private Object setValue( final PropertyDescriptor descriptor,
                             final Object[] args )
    {
        propertyValues.put( descriptor.getName(), args[0] );
        return null;
    }
}
