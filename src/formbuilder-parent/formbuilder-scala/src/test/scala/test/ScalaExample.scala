/*
 * Copyright (C) 2011 Andrey Yeremenok (eav1986__at__gmail__com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package test

import java.util.Date
import swing.BorderPanel
import scala.swing.BorderPanel.Position._
import reflect.BeanProperty
import org.formbuilder.Form
import org.formbuilder.scala.ScalaFormBuilder._
import org.formbuilder.scala.{PropertyName, Sample, ScalaFormBuilder}
import org.formbuilder.mapping.typemapper.impl.{NumberToSpinnerMapper, BooleanToCheckboxMapper, StringToTextFieldMapper}
import org.formbuilder.scala.javaconversion.toScala
import org.formbuilder.scala.javaconversion.toScala._

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
      bind(person.getAge) to new NumberToSpinnerMapper
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

class Person(
  @BeanProperty name: String,
  @BeanProperty description: String,
  @BeanProperty age: Int,
  @BeanProperty birthDate: Date,
  @BeanProperty gender: Boolean
)
