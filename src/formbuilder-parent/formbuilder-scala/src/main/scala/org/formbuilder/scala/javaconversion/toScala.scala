package org.formbuilder.scala.javaconversion

import org.formbuilder.TypeMapper
import org.formbuilder.mapping.change.ChangeHandler
import javax.swing.JComponent

/**
 * @author eav
 * Date: 02.08.11
 * Time: 19:56
 */
object toScala {
//  def apply[JC <: JComponent]( m: TypeMapper[JC, java.lang.Integer] ) = new IntMapper[JC](m)

  def apply[JC <: JComponent]( m: TypeMapper[JC, java.lang.Boolean] ) = new BooleanMapper[JC](m)
}

//class IntMapper[JC <: JComponent]( val m: TypeMapper[JC, _ <: java.lang.Number] ) extends TypeMapper[JC, Int] {
//  def handleChanges( editorComponent: JC, changeHandler: ChangeHandler ) {
//    m.handleChanges(editorComponent, changeHandler)
//  }
//
//  def createEditorComponent( ) = m.createEditorComponent()
//
//  def getValue( editorComponent: JC ) = m.getValue(editorComponent)
//
//  def getValueClass = classOf[Int]
//
//  def setValue( editorComponent: JC, value: Int ) {
//    m.setValue(editorComponent, value)
//  }
//}

class BooleanMapper[JC <: JComponent]( val m: TypeMapper[JC, java.lang.Boolean] ) extends TypeMapper[JC, Boolean] {
  def handleChanges( editorComponent: JC, changeHandler: ChangeHandler ) {
    m.handleChanges(editorComponent, changeHandler)
  }

  def createEditorComponent( ) = m.createEditorComponent()

  def getValue( editorComponent: JC ) = m.getValue(editorComponent)

  def getValueClass = classOf[Boolean]

  def setValue( editorComponent: JC, value: Boolean ) {
    m.setValue(editorComponent, value)
  }
}
