package test

import java.util.Date
import swing.BorderPanel
import scala.swing.BorderPanel.Position._

import org.formbuilder.Form
import org.formbuilder.scala.useSample
import org.formbuilder.mapping.typemapper.impl.{BooleanToCheckboxMapper => CBMapper, StringToTextFieldMapper => TFMapper}
import com.sun.xml.internal.ws.developer.UsesJAXBContext
import reflect.BeanProperty

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
    {(person, conf) =>
      conf + (person.getGender, new CBMapper)
      // todo this invocation seems too ugly
      conf.add[String](new TFMapper, person.getName, person.getDescription)
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

