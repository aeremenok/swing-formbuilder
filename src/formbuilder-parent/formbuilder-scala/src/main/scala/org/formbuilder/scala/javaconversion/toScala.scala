/*
 * Copyright (C) 2011 Andrey Yeremenok (eav1986__at__gmail__com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package org.formbuilder.scala.javaconversion

import org.formbuilder.TypeMapper
import org.formbuilder.mapping.change.ChangeHandler
import javax.swing.JComponent
import scala.Predef._
import scala.{Double, Float, Long, Boolean}
import java.lang.{Boolean => JBoolean, Integer => JInteger, Long => JLong, Byte => JByte, Short => JShort, Float => JFloat, Double => JDouble, Number => JNumber}

/**
 * @author eav
 * Date: 02.08.11
 * Time: 19:56
 */
object toScala {
  implicit def toIntScalaMapper[JC <: JComponent, JI >: JInteger <: JNumber]( m: TypeMapper[JC, JI] ): TypeMapper[JC, Int] =
    new ScalaIntMapper[JC, JI](m)

  implicit def toLongScalaMapper[JC <: JComponent, JL >: JLong <: JNumber]( m: TypeMapper[JC, JL] ): TypeMapper[JC, Long] =
    new ScalaLongMapper[JC, JL](m)

  implicit def toByteScalaMapper[JC <: JComponent, JB >: JByte <: JNumber]( m: TypeMapper[JC, JB] ): TypeMapper[JC, Byte] =
    new ScalaByteMapper[JC, JB](m)

  implicit def toShortScalaMapper[JC <: JComponent, JS >: JShort <: JNumber]( m: TypeMapper[JC, JS] ): TypeMapper[JC, Short] =
    new ScalaShortMapper[JC, JS](m)

  implicit def toFloatScalaMapper[JC <: JComponent, JF >: JFloat <: JNumber]( m: TypeMapper[JC, JF] ): TypeMapper[JC, Float] =
    new ScalaFloatMapper[JC, JF](m)

  implicit def toDoubleScalaMapper[JC <: JComponent, JD >: JDouble <: JNumber]( m: TypeMapper[JC, JD] ): TypeMapper[JC, Double] =
    new ScalaDoubleMapper[JC, JD](m)

  implicit def toBooleanScalaMapper[JC <: JComponent]( m: TypeMapper[JC, JBoolean] ): TypeMapper[JC, Boolean] =
    new ScalaBooleanMapper[JC](m)
}

abstract class AbstractDelegateMapper[ScalaType, JavaType, JC <: JComponent]( protected val delegate: TypeMapper[JC, JavaType] )
          ( implicit manifest: Manifest[ScalaType] )

        extends TypeMapper[JC, ScalaType] {
  def handleChanges( editorComponent: JC, changeHandler: ChangeHandler ) {
    delegate.handleChanges(editorComponent, changeHandler)
  }

  def getValueClass = manifest.erasure.asInstanceOf[Class[ScalaType]]

  def createEditorComponent( ) = delegate.createEditorComponent()

  def getValue( editorComponent: JC ): ScalaType = java2scala(delegate.getValue(editorComponent))

  def setValue( editorComponent: JC, value: ScalaType ) {
    delegate.setValue(editorComponent, scala2java(value))
  }

  def java2scala( j: JavaType ): ScalaType

  // todo T{def box()}?
  def scala2java( s: ScalaType ): JavaType
}

class ScalaBooleanMapper[JC <: JComponent]( val m: TypeMapper[JC, JBoolean] )
        extends AbstractDelegateMapper[Boolean, JBoolean, JC](m) {
  def java2scala( j: JBoolean ) = j

  def scala2java( s: Boolean ) = s
}

class ScalaIntMapper[JC <: JComponent, JI >: JInteger <: JNumber]( val m: TypeMapper[JC, JI] )
        extends AbstractDelegateMapper[Int, JI, JC](m) {
  def java2scala( j: JI ) = j.intValue()

  def scala2java( s: Int ) = s
}

class ScalaLongMapper[JC <: JComponent, JL >: JLong <: JNumber]( val m: TypeMapper[JC, JL] )
        extends AbstractDelegateMapper[Long, JL, JC](m) {
  def java2scala( j: JL ) = j.longValue()

  def scala2java( s: Long ) = s
}

class ScalaByteMapper[JC <: JComponent, JB >: JByte <: JNumber]( val m: TypeMapper[JC, JB] )
        extends AbstractDelegateMapper[Byte, JB, JC](m) {
  def java2scala( j: JB ) = j.byteValue()

  def scala2java( s: Byte ) = s
}

class ScalaShortMapper[JC <: JComponent, JS >: JShort <: JNumber]( val m: TypeMapper[JC, JS] )
        extends AbstractDelegateMapper[Short, JS, JC](m) {
  def java2scala( j: JS ) = j.byteValue()

  def scala2java( s: Short ) = s
}

class ScalaFloatMapper[JC <: JComponent, JF >: JFloat <: JNumber]( val m: TypeMapper[JC, JF] )
        extends AbstractDelegateMapper[Float, JF, JC](m) {
  def java2scala( j: JF ) = j.floatValue()

  def scala2java( s: Float ) = s
}

class ScalaDoubleMapper[JC <: JComponent, JD >: JDouble <: JNumber]( val m: TypeMapper[JC, JD] )
        extends AbstractDelegateMapper[Double, JD, JC](m) {
  def java2scala( j: JD ) = j.doubleValue()

  def scala2java( s: Double ) = s
}
