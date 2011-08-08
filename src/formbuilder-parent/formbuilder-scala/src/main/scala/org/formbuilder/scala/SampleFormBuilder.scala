package org.formbuilder.scala

import org.formbuilder.mapping.form.FormFactory
import javax.swing.JComponent
import swing.Component
import org.formbuilder.mapping.beanmapper.{SampleContext, SampleBeanMapper}
import org.formbuilder.mapping.typemapper.{GetterMapper, GetterConfig}
import org.formbuilder.{Form, TypeMapper, FormBuilder}

/**
 * @author eav
 * Date: 08.08.11
 * Time: 15:02
 */
class SampleFormBuilder[B]( implicit m: Manifest[B] ) {
  type tm[T] = TypeMapper[_ <: JComponent, _ <: T]
  type getterCall[T] = () => T
  private val javaBuilder: FormBuilder[B] = FormBuilder.map(m.erasure.asInstanceOf[Class[B]])

  def doValidation( v: Boolean ) = javaBuilder.doValidation(v)

  def formsOf( factory: FormFactory ) = javaBuilder.formsOf(factory)

  def useTypeMappers( typeMappers: tm[_]* ) {
    typeMappers.foreach(javaBuilder.use(_))
  }

  def useComponent( builderFunction: (B, LabelContext[B], EditorContext[B]) => Component ) =
    javaBuilder.`with`(new SampleBeanMapper[B] {
      def mapBean( sample: B, context: SampleContext[B] ): JComponent = {
        val labelOf = new LabelContext[B](context)
        val editorOf = new EditorContext[B](context)
        builderFunction(sample, labelOf, editorOf).peer
      }
    })

  def useGetterBinding( getterMapping: (B, GetterBinder) => Unit ) =
    javaBuilder.useForGetters(new GetterMapper[B] {
      def mapGetters( beanSample: B, config: GetterConfig ) {
        getterMapping(beanSample, new GetterBinder(config))
      }
    })

  implicit def buildForm( ): Form[B] = javaBuilder.buildForm()

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

object SampleFormBuilder {
  implicit def toForm[B]( sfb: SampleFormBuilder[B] ) = sfb.buildForm()
}
