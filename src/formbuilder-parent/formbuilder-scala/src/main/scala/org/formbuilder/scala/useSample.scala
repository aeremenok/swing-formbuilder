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

  def apply[B](validate: Boolean = true,
               formsOf: FormFactory = FormFactories.REPLICATING,
               typeMappers: List[tm[Any]] = Nil)
              // ...
              (panelBuiler: (B, Context[B]) => Component)
              // ...
              (tc: GetterConf[B] => Unit)
              // ...
              (implicit m: Manifest[B]): Form[B] = {
    val beanClass = m.erasure.asInstanceOf[Class[B]]
    val mapper = new SampleBeanMapper[B] {
      def mapBean(sample: B, context: SampleContext[B]): JComponent =
        panelBuiler(sample, new Context[B](context)).peer
    }
    val getterMapper = new GetterMapper[B] {
      def mapGetters(beanSample: B, config: GetterConfig) {
        tc(new GetterConf[B](beanSample, config))
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

  implicit def getSample[B](config: GetterConf[B]): B = config.sample
}

class Context[B](val ctx: SampleContext[B]) {
  def @@(ignored: Any) = Component wrap ctx.label(ignored)

  def ##(ignored: Any) = Component wrap ctx.editor(ignored)
}

class GetterConf[B](val sample: B, val conf: GetterConfig) {
  type tm[T] = TypeMapper[_ <: JComponent, _ <: T]

  def +[T](binding: (T, tm[T])): GetterConf[B] = {
    conf.use(binding._1, binding._2)
    this
  }
}
