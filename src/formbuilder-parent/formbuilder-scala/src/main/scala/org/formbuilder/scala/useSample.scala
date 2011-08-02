package org.formbuilder.scala

import org.formbuilder.mapping.beanmapper.{SampleBeanMapper, SampleContext}
import swing.Component
import javax.swing.JComponent
import org.formbuilder.mapping.form.{FormFactories, FormFactory}
import org.formbuilder.{TypeMapper, FormBuilder, Form}
import org.formbuilder.mapping.typemapper.{GetterMapper, GetterConfig}
import collection.immutable.Map

/**
 * @author eav
 * Date: 27.07.11
 * Time: 21:03
 */
object useSample {
  type tm[T] = TypeMapper[_ <: JComponent, _ <: T]
  type getterCall[T] = () => T

  def apply[B](
    validate: Boolean = true,
    formsOf: FormFactory = FormFactories.REPLICATING,
    typeMappers: List[tm[Any]] = Nil
  )
  ( panelBuilder: (B, LabelContext[B], EditorContext[B]) => Component )
  ( getterMapping: (B, GetterConf) => Unit )
  ( implicit m: Manifest[B] )
  : Form[B] = {
    val beanClass = m.erasure.asInstanceOf[Class[B]]

    val mapper = new SampleBeanMapper[B] {
      def mapBean( sample: B, context: SampleContext[B] ): JComponent = {
        val labelOf = new LabelContext[B](context)
        val editorOf = new EditorContext[B](context)
        panelBuilder(sample, labelOf, editorOf).peer
      }
    }

    val getterMapper = new GetterMapper[B] {
      def mapGetters( beanSample: B, config: GetterConfig ) {
        getterMapping(beanSample, new GetterConf(config))
      }
    }

    //    todo varargs
    //    val arrayOfMappers: Array[TypeMapper[_ <: JComponent, _]] = typeMappers.toArray
    FormBuilder
    .map(beanClass)
    .`with`(mapper)
    .doValidation(validate)
    .formsOf(formsOf)
    .useForGetters(getterMapper)
    .buildForm()
  }

  class LabelContext[B]( val ctx: SampleContext[B] ) {
    def apply( getter: getterCall[_] ) = Component wrap ctx.label(getter())
  }

  class EditorContext[B]( val ctx: SampleContext[B] ) {
    def apply( getter: getterCall[_] ) = Component wrap ctx.editor(getter())
  }

  class GetterConf( val conf: GetterConfig ) {
    def +[T]( binding: (getterCall[T], tm[T]) ): GetterConf = {
      conf.use(binding._1(), binding._2)
      this
    }

    def add[T]( mapper: tm[T], getters: getterCall[T]* ): GetterConf = {
      getters foreach {getter =>
        conf.use(getter(), mapper)
      }
      this
    }
  }
}
