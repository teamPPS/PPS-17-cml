package cml.view

import cml.view.utils.TileConfig._
import javafx.scene.control.TextArea
import javafx.scene.image.ImageView
import javafx.scene.input._
import javafx.scene.layout.GridPane
import javafx.scene.{Node, SnapshotParameters}

/**
  * Setup handlers with costume settings
  * @author Monica Gondolini, ecavina
  */
trait HandlerSetup {

  /**
    * Setup handlers for the village
    * @param grid to handle
    * @param info to show information
    */
  def setupVillageHandlers(grid: GridPane, info: TextArea): Unit

  /**
    * Setup handlers for buildings menu
    * @param grid to handle
    * @param info to show information
    */
  def setupBuildingsHandlers(grid: GridPane, info: TextArea): Unit
}

trait Handler {
  def handle(elem: Node, info: TextArea): Unit
}

object Handler {

  val handleVillage: Handler = {
    (elem: Node, info: TextArea) =>
      addClickHandler(elem, info)
      addDragAndDropTargetHandler(elem, info)
  }

  val handleBuilding: Handler = {
    (elem: Node, info: TextArea) =>
      for(tile <- tileSet){
        addDragAndDropSourceHandler(tile, info)
      }
  }

  private def addClickHandler(n: Node, info: TextArea): Unit = {
    n setOnMouseClicked(_ => {
      val y = GridPane.getColumnIndex(n)
      val x = GridPane.getRowIndex(n)
      info setText "Mouse clicked in coords: ("+x+","+y+")\n" //TODO elemento selezionato dal click
    })
  }

  private def addDragAndDropSourceHandler(t: Tile, info: TextArea): Unit = {
    val canvas = t.imageSprite
    canvas setOnMouseClicked(_ => { //TODO hover (tooltip venuta male)
        info setText "Element selected: "+ t.description + "Price: [PRICE]"
    })
    canvas setOnDragDetected((event: MouseEvent) => {
      val dragBoard: Dragboard = canvas startDragAndDrop TransferMode.COPY
      val image = canvas.snapshot(new SnapshotParameters, null)
      dragBoard setDragView image
      val content: ClipboardContent = new ClipboardContent
      content putString t.description
      dragBoard setContent content
      info setText "Dragged element " + dragBoard.getString
      event consume()
    })
  }

  private def addDragAndDropTargetHandler(n: Node, info: TextArea): Unit = {
    n setOnDragOver ((event: DragEvent) => {
      event acceptTransferModes TransferMode.COPY
      event consume()
    })

    n setOnDragDropped ((event: DragEvent) => {
      val dragBoard: Dragboard = event getDragboard()
      val newTile = tileSet.filter(t => t.description.equals(dragBoard.getString)).head
      n match {
        case i: ImageView => i setImage newTile.imageSprite.snapshot(new SnapshotParameters, null)
        case _ => throw new ClassCastException
      }
      val y = GridPane.getColumnIndex(n)
      val x = GridPane.getRowIndex(n)
      info setText "Dropped element " + dragBoard.getString + " in coordinates (" + x + " - " + y + ")"
      event consume()
      // UPDATE MODEL e send al server
    })
  }

}

object ConcreteHandlerSetup extends HandlerSetup {

  private def setHandlers(grid: GridPane, info: TextArea, handler: Handler): Unit = {
    val children = grid.getChildren
    children forEach(handler.handle(_, info))
  }

  override def setupVillageHandlers(grid: GridPane, info: TextArea): Unit = setHandlers(grid, info, Handler.handleVillage)

  override def setupBuildingsHandlers(grid: GridPane, info: TextArea): Unit = setHandlers(grid, info, Handler.handleBuilding)
}