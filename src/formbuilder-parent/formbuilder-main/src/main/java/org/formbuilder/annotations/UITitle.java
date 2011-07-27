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
package org.formbuilder.annotations;

import org.formbuilder.mapping.metadata.AnnotationMetaData;

import java.beans.PropertyDescriptor;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Adds a human-readable title to a property. Newly created label for such property will have text with {@link
 * UITitle#value()}
 *
 * @author aeremenok 2010
 * @see AnnotationMetaData#getTitle(PropertyDescriptor)
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( {ElementType.METHOD, ElementType.FIELD} )
public @interface UITitle
{
    String value();
}
