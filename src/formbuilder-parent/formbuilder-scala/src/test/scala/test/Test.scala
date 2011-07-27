package test

import java.util.Date
import org.formbuilder.mapping.typemapper.{GetterConfig, GetterMapper}
import org.formbuilder.{Form, FormBuilder}
import org.formbuilder.mapping.typemapper.impl.StringToTextFieldMapper
import org.formbuilder.scala.SwingMap
import java.awt.BorderLayout
import javax.swing.JPanel
import swing.BorderPanel

/**
 * @author eav
 * Date: 27.07.11
 * Time: 18:43
 */
class Test {
  def testUsualJava() {
    val form1 = FormBuilder.map(classOf[Person]).buildForm()
    val mapper = new
                    GetterMapper[Person] {
      def mapGetters(beanSample: Person,
                     config: GetterConfig) {
        config.use(beanSample.description, new StringToTextFieldMapper)
      }
    }
    val form3 = FormBuilder.map(classOf[Person]).useForGetters(mapper).buildForm()
  }

  def desired() {
    val form1: Form[Person] = SwingMap[Person] withFun {
      (samplePerson, ctx) => new BorderPanel {
        add(ctx.label(samplePerson.name), BorderPanel.Position.North)
        add(ctx.label(samplePerson.description), BorderPanel.Position.Center)
      }
    } buildForm ()

    val form2: Form[Person] = SwingMap[Person] useForGetters {
      (samplePerson, config) =>
        config.use(samplePerson.name, new StringToTextFieldMapper)
        config.use(samplePerson.description, new StringToTextFieldMapper)
    } buildForm ()

  }

  def veryDesired(){

  }
}

case class Person(name: String,
                  description: String,
                  age: Int,
                  birthDate: Date,
                  gender: Boolean)

