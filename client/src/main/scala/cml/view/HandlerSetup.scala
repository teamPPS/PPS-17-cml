package cml.view

import java.util.Observable

import cml.view.utils.TileConfig._
import javafx.collections.ObservableList
import javafx.scene.Node
import javafx.scene.control.TextArea
import javafx.scene.image.ImageView
import javafx.scene.layout.GridPane

/**
  * @author Monica Gondolini, ecavina
  */

trait HandlerSetup {
  def setupVillageHandlers(grid: GridPane, info: TextArea): Unit
  def setupBuildingsHandlers(grid: GridPane, info: TextArea): Unit
}

trait Handler {
  def handle(elem: Node, info: TextArea): Unit
}

object Handler{

  val handleVillage: Handler = {
    (elem: Node, info: TextArea) =>
      addClickHandler(elem, info)

  }

  val handleBuilding: Handler = {
    (elem: Node, info: TextArea) => ???

  }

  private def addClickHandler(n: Node, info: TextArea): Unit = {
    n setOnMouseClicked(mouseEvent => {
      val y = GridPane.getColumnIndex(n)
      val x = GridPane.getRowIndex(n)
      println("Mouse clicked in coords: ("+x+","+y+")")
      info setText("Mouse clicked in coords: ("+x+","+y+")")
    })
  }

  private def addDragAndDropSourceHandler(t: Tile): Unit = ???

  private def addDragAndDropTargetHandler(n: Node): Unit = ???

}

object ConcreteHandlerSetup extends HandlerSetup {

  private def setHandlers(grid: GridPane, info: TextArea, handler: Handler): Unit = {
    val children: ObservableList[Node] = grid.getChildren
    children.forEach(handler.handle(_, info))
  }

  override def setupVillageHandlers(grid: GridPane, info: TextArea): Unit = setHandlers(grid, info, Handler.handleVillage)

  override def setupBuildingsHandlers(grid: GridPane, info: TextArea): Unit = setHandlers(grid, info, Handler.handleBuilding)
}