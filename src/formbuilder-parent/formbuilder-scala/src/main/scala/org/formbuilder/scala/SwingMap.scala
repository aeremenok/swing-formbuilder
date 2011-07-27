package org.formbuilder.scala

import javax.swing.JComponent
import org.formbuilder.mapping.BeanMappingContext
import org.formbuilder.FormBuilder
import org.formbuilder.mapping.typemapper.{GetterMapper, GetterConfig}
import org.formbuilder.mapping.beanmapper.SampleBeanMapper
/**
 * @author eav
 * Date: 27.07.11
 * Time: 19:08
 */
object SwingMap {
  def apply[B](implicit m: Manifest[B]): ScalaBuilder[B] =
    new ScalaBuilder[B](FormBuilder.map(classOf[B]))
}

class ScalaBuilder[B](val b: FormBuilder[B]) {
  def withFun(f: (B, BeanMappingContext[B]) => JComponent): ScalaBuilder[B] = {
    b `with` new SampleBeanMapper[B] {
      def mapBean(sampleBean: B,
                  context: BeanMappingContext[B]): JComponent =
        f(sampleBean, context)
    }
    this
  }

  def useForGetters(f: (B, GetterConfig) => Unit): ScalaBuilder[B] = {
    b useForGetters new GetterMapper[B] {
      def mapGetters(beanSample: B,
                     config: GetterConfig) {
        f(beanSample, config)
      }
    }
    this
  }
}
