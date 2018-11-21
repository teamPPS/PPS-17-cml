package cml.view

import javafx.scene.control.TextArea
import javafx.scene.image.ImageView
import javafx.scene.layout.GridPane



trait HandlerSetup {
  def setupVillageHandlers(grid: GridPane, info: TextArea): Unit
  def setupBuildingsHandlers(grid: GridPane, info: TextArea): Unit

}

trait Handler {
  def handle(elem: ImageView, info: TextArea): Unit
}

object Handler{


}

object ConcreteHandlerSetup extends HandlerSetup {

  private def setHandlers(grid: GridPane, info: TextArea, handler: Handler): Unit = ???

  override def setupVillageHandlers(grid: GridPane, info: TextArea): Unit = ???

  override def setupBuildingsHandlers(grid: GridPane, info: TextArea): Unit = ???
}