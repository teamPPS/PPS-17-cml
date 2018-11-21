package cml.view

import javafx.scene.control.TextArea
import javafx.scene.layout.GridPane


trait HandlerSetup {
  def setupVillageHandlers(grid: GridPane, info: TextArea): Unit
  def setupBuildingsHandlers(grid: GridPane, info: TextArea): Unit

}

object ConcreteHandlerSetup extends HandlerSetup {

  override def setupVillageHandlers(grid: GridPane, info: TextArea): Unit = ???

  override def setupBuildingsHandlers(grid: GridPane, info: TextArea): Unit = ???
}