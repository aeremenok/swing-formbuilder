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
package org.formbuilder.scala

import org.formbuilder.mapping.form.FormFactory
import javax.swing.JComponent
import swing.Component
import org.formbuilder.mapping.typemapper.{GetterMapper, GetterConfig}
import org.formbuilder.{Form, TypeMapper, FormBuilder}
import org.formbuilder.mapping.beanmapper.{PropertyNameBeanMapper, PropertyNameContext, SampleContext, SampleBeanMapper}

/**
 * @author eav
 * Date: 08.08.11
 * Time: 15:02
 */
class ScalaFormBuilder[B]( implicit m: Manifest[B] ) {
  type tm[T] = TypeMapper[_ <: JComponent, _ <: T]
  type getterCall[T] = () => T
  protected[scala] val javaBuilder: FormBuilder[B] = FormBuilder.map(m.erasure.asInstanceOf[Class[B]])

  def doValidation( v: Boolean ) = javaBuilder.doValidation(v)

  def formsOf( factory: FormFactory ) = javaBuilder.formsOf(factory)

  def useTypeMappers( typeMappers: tm[_]* ) {
    typeMappers.foreach(javaBuilder.use(_))
  }

  def buildForm( ): Form[B] = javaBuilder.buildForm()
}

trait Sample[B] {
  self: ScalaFormBuilder[B] =>
  def useComponent( builderFunction: (B, LabelContext[B], EditorContext[B]) => Component ) =
    javaBuilder.`with`(new SampleBeanMapper[B] {
      def mapBean( sample: B, context: SampleContext[B] ): JComponent = {
        val labelOf = new LabelContext[B](context)
        val editorOf = new EditorContext[B](context)
        builderFunction(sample, labelOf, editorOf).peer
      }
    })

  def usePropertyBinding( getterMapping: (B, GetterBinder) => Unit ) =
    javaBuilder.useForGetters(new GetterMapper[B] {
      def mapGetters( beanSample: B, config: GetterConfig ) {
        getterMapping(beanSample, new GetterBinder(config))
      }
    })

  class LabelContext[B]( val ctx: SampleContext[B] ) {
    def apply( getter: getterCall[_] ) = Component wrap ctx.label(getter())
  }

  class EditorContext[B]( val ctx: SampleContext[B] ) {
    def apply( getter: getterCall[_] ) = Component wrap ctx.editor(getter())
  }

  class GetterBinder( val conf: GetterConfig ) {
    def apply[T]( getters: getterCall[T]* ) = new GetterBinding[T](conf, getters.toList)

    class GetterBinding[T]( val conf: GetterConfig, val getters: List[getterCall[T]] ) {
      def to( mapping: tm[T] ) {
        getters foreach {getter => conf.use(getter(), mapping)}
      }
    }

  }

}

trait PropertyName[B] {
  self: ScalaFormBuilder[B] =>
  def useCompoment( builderFunction: (LabelContext[B], EditorContext[B]) => Component ) =
    javaBuilder.`with`(new PropertyNameBeanMapper[B] {
      def mapBean( context: PropertyNameContext[B] ) = {
        val labelOf = new LabelContext[B](context)
        val editorOf = new EditorContext[B](context)
        builderFunction(labelOf, editorOf).peer
      }
    })

  def usePropertyBinding( mappings: (String, TypeMapper[_ <: JComponent, _])* ) {
    mappings foreach {mapping =>
      javaBuilder.useForProperty(mapping._1, mapping._2)
    }
  }

  class LabelContext[B]( val ctx: PropertyNameContext[B] ) {
    def apply( propertyName: String ) = Component wrap ctx.label(propertyName)
  }

  class EditorContext[B]( val ctx: PropertyNameContext[B] ) {
    def apply( propertyName: String ) = Component wrap ctx.editor(propertyName)
  }

}

object ScalaFormBuilder {
  implicit def toForm[B]( sfb: ScalaFormBuilder[B] ) = sfb.buildForm()
}
