package org.formbuilder.scala

import org.formbuilder.mapping.form.{FormFactories, FormFactory}
import javax.swing.JComponent
import swing.Component
import org.formbuilder.mapping.beanmapper.{SampleContext, SampleBeanMapper}
import org.formbuilder.mapping.typemapper.{GetterConfig, GetterMapper}
import org.formbuilder.{FormBuilder, Form, TypeMapper}
import org.formbuilder.scala.useSample.{EditorContext, GetterBinder, LabelContext}

/**
 * @author eav
 * Date: 02.08.11
 * Time: 16:26
 */
object sample {
  type tm[T] = TypeMapper[_ <: JComponent, _ <: T]
  type getterCall[T] = () => T

  abstract class SampleForm[B](
    validate: Boolean = true,
    formsOf: FormFactory = FormFactories.REPLICATING,
    typeMappers: List[tm[Any]] = Nil,
    panelBuilder: (B, LabelContext[B], EditorContext[B]) => Component
  )( implicit m: Manifest[B] ) extends Form[B] {
    lazy val delegate: Form[B] = buildForm()

    private def buildForm( ): Form[B] = {
      val beanClass = m.erasure.asInstanceOf[Class[B]]

      val mapper = new SampleBeanMapper[B] {
        def mapBean( sample: B, context: SampleContext[B] ): JComponent = {
          val labelOf = new LabelContext[B](context)
          val editorOf = new EditorContext[B](context)
          panelBuilder(sample, labelOf, editorOf).peer
        }
      }

      //      val getterMapper = new GetterMapper[B] {
      //        def mapGetters( beanSample: B, config: GetterConfig ) {
      //          getterMapping(beanSample, new GetterConf(config))
      //        }
      //      }
      //    todo varargs
      //    val arrayOfMappers: Array[TypeMapper[_ <: JComponent, _]] = typeMappers.toArray
      FormBuilder.map(beanClass).`with`(mapper).doValidation(validate).formsOf(formsOf)
      //      .useForGetters(getterMapper)
      .buildForm()
    }

    def buildPanel( beanSample: B, labelOf: LabelContext[B], editorOf: EditorContext[B] ): Component

    def asComponent( ) = delegate.asComponent()

    def getValue( ) = delegate.getValue()

    def setValue( bean: B ) {delegate.setValue(bean)}
  }

}
