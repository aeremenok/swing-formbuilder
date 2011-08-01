package test

import java.util.Date
import swing.BorderPanel
import scala.swing.BorderPanel.Position._
import org.formbuilder.mapping.typemapper.impl.{BooleanToCheckboxMapper, StringToTextFieldMapper}
import org.formbuilder.scala.useSample;
import org.formbuilder.scala.GetterConf
import org.formbuilder.{TypeMapper, Form};

/**
 * @author eav
 * Date: 27.07.11
 * Time: 18:43
 */
class Test {
  def veryDesired() {
    val form3: Form[Person] = useSample[Person](validate = false,
                                                typeMappers = new StringToTextFieldMapper :: Nil
//                                                list = (p => p.name, "name")
//                                                       :: (p => p.description, "desc")
//                                                       :: Nil
                                                )
    {(person, ctx) =>
      new BorderPanel {
        add(ctx @@ person.name, North)
        add(ctx ## person.description, Center)
      }
    }
    {(person, conf) =>
      conf + (person.name, new StringToTextFieldMapper) + (person.gender, new BooleanToCheckboxMapper)
    }{sample => Map(sample.name -> "", sample.gender -> "")
         }
//    val list : List[(Person => Unit, TypeMapper[])] = Nil
  }
}

case class Person(name: String, description: String, age: Int, birthDate: Date, gender: Boolean)

