package cml.view

import java.util.Observable

import cml.view.utils.TileConfig._
import javafx.collections.ObservableList
import javafx.scene.{Node, SnapshotParameters}
import javafx.scene.control.TextArea
import javafx.scene.image.ImageView
import javafx.scene.input.{DragEvent, Dragboard, TransferMode}
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

  val baseTile = tileSet.filter(t => t.description.equals("TERRAIN")).head

  val handleVillage: Handler = {
    (elem: Node, info: TextArea) =>
      val imageTile = new ImageView()
      imageTile.setImage(baseTile.imageSprite.snapshot(new SnapshotParameters, null))
      addClickHandler(elem, info)
      addDragAndDropTargetHandler(imageTile, info)
  }

  val handleBuilding: Handler = {
    (elem: Node, info: TextArea) => ???

  }

  private def addClickHandler(n: Node, info: TextArea): Unit = {
    n setOnMouseClicked(mouseEvent => {
      val y = GridPane.getColumnIndex(n)
      val x = GridPane.getRowIndex(n)
      println("Mouse clicked in coords: ("+x+","+y+")")
      info setText "Mouse clicked in coords: ("+x+","+y+")"
    })
  }

  private def addDragAndDropSourceHandler(t: Tile): Unit = ???

  private def addDragAndDropTargetHandler(i: ImageView, info: TextArea): Unit = {
    i setOnDragOver ((event: DragEvent) => {
      event acceptTransferModes TransferMode.COPY
      event consume()
    })

    i setOnDragDropped ((event: DragEvent) => {
      val dragBoard: Dragboard = event getDragboard()
      val newTile = tileSet.filter(t => t.description.equals(dragBoard.getString)).head
      i setImage newTile.imageSprite.snapshot(new SnapshotParameters, null)
      val y = GridPane.getColumnIndex(i)
      val x = GridPane.getRowIndex(i)
      println("Dropped element " + dragBoard.getString + " in coordinates (" + x + " - " + y + ")")
      info setText "Dropped element " + dragBoard.getString + " in coordinates (" + x + " - " + y + ")"
      event consume()
      // UPDATE MODEL e send al server
    })
  }

}

object ConcreteHandlerSetup extends HandlerSetup {

  private def setHandlers(grid: GridPane, info: TextArea, handler: Handler): Unit = {
    val children = grid.getChildren
    children.forEach(handler.handle(_, info))
  }

  override def setupVillageHandlers(grid: GridPane, info: TextArea): Unit = setHandlers(grid, info, Handler.handleVillage)

  override def setupBuildingsHandlers(grid: GridPane, info: TextArea): Unit = setHandlers(grid, info, Handler.handleBuilding)
}