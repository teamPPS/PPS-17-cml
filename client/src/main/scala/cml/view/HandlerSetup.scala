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

  val handleVillage: Handler = {
    (elem: ImageView, info: TextArea) => ???

  }

  val handleBuilding: Handler = {
    (elem: ImageView, info: TextArea) => ???

  }

  private def addClickHandler(i: ImageView): Unit = ???

  private def addDragAndDropSourceHandler(t: Tile): Unit = ???

  private def addDragAndDropTargetHandler(i: ImageView): Unit = ???

}

object ConcreteHandlerSetup extends HandlerSetup {

  private def setHandlers(grid: GridPane, info: TextArea, handler: Handler): Unit = ???

  override def setupVillageHandlers(grid: GridPane, info: TextArea): Unit = ???

  override def setupBuildingsHandlers(grid: GridPane, info: TextArea): Unit = ???
}