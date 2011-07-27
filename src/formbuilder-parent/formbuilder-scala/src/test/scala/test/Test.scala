package test

import java.util.Date
import org.formbuilder.Form
import swing.BorderPanel
import scala.swing.BorderPanel.Position._
import org.formbuilder.mapping.typemapper.impl.{BooleanToCheckboxMapper, StringToTextFieldMapper}
import org.formbuilder.scala.{GetterConf, useSample};

/**
 * @author eav
 * Date: 27.07.11
 * Time: 18:43
 */
class Test {
  def veryDesired() {
    val form3: Form[Person] = useSample[Person](validate = false,
                                                typeMappers = new StringToTextFieldMapper :: Nil)
    {(sample, ctx) =>
      new BorderPanel {
        add(ctx @@ sample.name, North)
        add(ctx ## sample.description, Center)
      }
    }
    {(sample, conf) =>
      conf.use(sample.name, new StringToTextFieldMapper).use(sample.gender, new BooleanToCheckboxMapper)
    }
  }
}

case class Person(name: String, description: String, age: Int, birthDate: Date, gender: Boolean)

