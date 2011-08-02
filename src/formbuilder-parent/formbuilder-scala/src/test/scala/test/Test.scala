package test

import java.util.Date
import swing.BorderPanel
import scala.swing.BorderPanel.Position._
import org.formbuilder.scala.useSample._;
import org.formbuilder.Form
import org.formbuilder.scala.useSample
import org.formbuilder.mapping.typemapper.impl.{BooleanToCheckboxMapper => CBMapper, StringToTextFieldMapper => TFMapper}

/**
 * @author eav
 * Date: 27.07.11
 * Time: 18:43
 */
class Test {
  def desired() {
    val form: Form[Person] = useSample[Person]()
    {(person, ctx) => new BorderPanel {
          add(ctx @@ person.name, North)
          add(ctx ## person.description, Center)
      }
    }
    {personConf =>
        personConf + (personConf.name, new TFMapper) + (personConf.gender, new CBMapper)
        personConf + (personConf.description, new TFMapper)
    }
  }
}

case class Person(name: String, description: String, age: Int, birthDate: Date, gender: Boolean)

