package test

import java.util.Date
import swing.BorderPanel
import scala.swing.BorderPanel.Position._

import org.formbuilder.scala.useSample
import reflect.BeanProperty
import org.formbuilder.Form
import org.formbuilder.mapping.typemapper.impl.{NumberToSpinnerMapper, BooleanToCheckboxMapper, StringToTextFieldMapper}
import org.formbuilder.scala.javaconversion.toScala

/**
 * @author eav
 * Date: 27.07.11
 * Time: 18:43
 */
class Test {
  def desired() {
    val form: Form[Person] = useSample[Person]()
    {(person, labelOf, editorOf) => new BorderPanel {
        add(labelOf(person.getName), West)
        add(editorOf(person.getName), Center)
    }
    }
    {(person, bind) =>
      bind(person.getGender).to(toScala(new BooleanToCheckboxMapper))
      bind(person.getName, person.getDescription).to(new StringToTextFieldMapper)
    }

  }
}

case class Person(
  @BeanProperty name: String,
  @BeanProperty description: String,
  @BeanProperty age: Int,
  @BeanProperty birthDate: Date,
  @BeanProperty gender: Boolean
)
