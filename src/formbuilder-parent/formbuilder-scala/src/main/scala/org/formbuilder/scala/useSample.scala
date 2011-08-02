package org.formbuilder.scala

import org.formbuilder.mapping.beanmapper.{SampleBeanMapper, SampleContext}
import swing.Component
import javax.swing.JComponent
import org.formbuilder.mapping.form.{FormFactories, FormFactory}
import org.formbuilder.{TypeMapper, FormBuilder, Form}
import org.formbuilder.mapping.typemapper.{GetterMapper, GetterConfig}

/**
 * @author eav
 * Date: 27.07.11
 * Time: 21:03
 */
object useSample {
  type tm[T] = TypeMapper[_ <: JComponent, _ <: T]
  type getterCall[T] = () => T

  def apply[B](
    validate: Boolean = true, formsOf: FormFactory = FormFactories.REPLICATING, typeMappers: List[tm[Any]] = Nil
  )
            ( panelBuilder: (B, LabelContext[B], EditorContext[B]) => Component )
            ( getterMapping: (B, GetterBinder) => Unit )
            ( implicit m: Manifest[B] ): Form[B] = {
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
        getterMapping(beanSample, new GetterBinder(config))
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

  class GetterBinder( val conf: GetterConfig ) {
    def apply[T]( getters: getterCall[T]* ) = new GetterBinding[T](conf, getters.toList)

    class GetterBinding[T]( val conf: GetterConfig, val getters: List[getterCall[T]] ) {
      def to( mapping: tm[T] ) {
        getters foreach {getter =>
          conf.use(getter(), mapping)
        }
      }
    }

  }

}
