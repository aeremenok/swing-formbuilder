package test

import java.util.Date
import swing.BorderPanel
import scala.swing.BorderPanel.Position._
import reflect.BeanProperty
import org.formbuilder.Form
import org.formbuilder.mapping.typemapper.impl.{BooleanToCheckboxMapper, StringToTextFieldMapper}
import org.formbuilder.scala.javaconversion.toScala._
import org.formbuilder.scala.SampleFormBuilder
import org.formbuilder.scala.SampleFormBuilder._

/**
 * @author eav
 * Date: 27.07.11
 * Time: 18:43
 */
class ScalaExample {
  val form: Form[Person] = new SampleFormBuilder[Person] {
    doValidation(false)
    useComponent {( person, labelOf, editorOf ) =>
      new BorderPanel {
        add(labelOf(person.getName), West)
        add(editorOf(person.getName), Center)
      }
    }
    useGetterBinding {( person, bind ) =>
      bind(person.getGender) to new BooleanToCheckboxMapper
      bind(person.getName, person.getDescription) to new StringToTextFieldMapper
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
