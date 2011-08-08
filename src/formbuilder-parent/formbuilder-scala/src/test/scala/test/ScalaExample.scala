package test

import java.util.Date
import swing.BorderPanel
import scala.swing.BorderPanel.Position._
import reflect.BeanProperty
import org.formbuilder.Form
import org.formbuilder.mapping.typemapper.impl.{BooleanToCheckboxMapper, StringToTextFieldMapper}
import org.formbuilder.scala.javaconversion.toScala._
import org.formbuilder.scala.ScalaFormBuilder._
import org.formbuilder.scala.{PropertyName, Sample, ScalaFormBuilder}

/**
 * @author eav
 * Date: 27.07.11
 * Time: 18:43
 */
class ScalaExample {
  val form1: Form[Person] = new ScalaFormBuilder[Person] with Sample[Person] {
    doValidation(false)
    useComponent {( person, labelOf, editorOf ) =>
      new BorderPanel {
        layout(labelOf(person.getName)) = West
        layout(editorOf(person.getName)) = Center
      }
    }
    usePropertyBinding {( person, bind ) =>
      bind(person.getGender) to new BooleanToCheckboxMapper
      bind(person.getName, person.getDescription) to new StringToTextFieldMapper
    }
  }

  val form2: Form[Person] = new ScalaFormBuilder[Person] with PropertyName[Person] {
    useCompoment {(labelOf, editorOf) =>
      new BorderPanel {
        layout(labelOf("name")) = West
        layout(editorOf("name")) = Center
      }
    }

    usePropertyBinding("name" -> new StringToTextFieldMapper, "gender" -> new BooleanToCheckboxMapper)
  }
}

case class Person(
  @BeanProperty name: String,
  @BeanProperty description: String,
  @BeanProperty age: Int,
  @BeanProperty birthDate: Date,
  @BeanProperty gender: Boolean
)
