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
  def apply[B](validate: Boolean = true,
               formsOf: FormFactory = FormFactories.REPLICATING,
               typeMappers: List[TypeMapper[_ <: JComponent, _]] = Nil)
              (panelBuiler: (B, Context[B]) => Component)
              (typeConfigurator: (B, GetterConf) => Unit)
              (implicit m: Manifest[B]): Form[B] = {
    val beanClass = m.erasure.asInstanceOf[Class[B]]
    val mapper = new SampleBeanMapper[B] {
      def mapBean(sample: B, context: SampleContext[B]): JComponent =
        panelBuiler(sample, new Context[B](context)).peer
    }
    val getterMapper = new GetterMapper[B] {
      def mapGetters(beanSample: B, config: GetterConfig) {
        typeConfigurator(beanSample, new GetterConf(config))
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
}

class Context[B](val ctx: SampleContext[B]) {
  def @@(ignored: Any) = Component wrap ctx.label(ignored)

  def ##(ignored: Any) = Component wrap ctx.editor(ignored)
}

class GetterConf(val conf: GetterConfig) {
  def use[T](ignored: T, typeMapper: TypeMapper[_ <: JComponent, _ <: T]): GetterConf = {
    conf.use(ignored, typeMapper)
    this
  }
}
