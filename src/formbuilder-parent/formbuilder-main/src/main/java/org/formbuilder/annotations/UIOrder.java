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
package org.formbuilder.annotations;

import org.formbuilder.mapping.metadata.AnnotationMetaData;
import org.formbuilder.mapping.metadata.functions.AddOrder;

import java.beans.PropertyDescriptor;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Adds to property the order, in which it appears on automatically generated form. Newly created editors are sorted by
 * their properties' orders ascending.
 *
 * @author aeremenok Date: 30.07.2010 Time: 16:55:55
 * @see AddOrder
 * @see AnnotationMetaData#getOrder(PropertyDescriptor)
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( {ElementType.METHOD, ElementType.FIELD} )
public @interface UIOrder
{
    int value();
}
