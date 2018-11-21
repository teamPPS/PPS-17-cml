package cml.view

import cml.view.utils.TileConfig.{tileSet,slicer,spriteSheet}

import javafx.scene.SnapshotParameters
import javafx.scene.image.{Image, ImageView}
import javafx.scene.input._
import javafx.scene.layout.GridPane

/**
  * Initialize Game GridPanes with costume settings
  * @author ecavina, Monica Gondolini
  */
trait GridInitializer {

  /**
    * Initialize a grid as a buildings menu
    * @param grid to initialize
    */
  def initializeBuildingsMenu(grid: GridPane)

  /**
    * Initialize a grid as a village
    * @param grid to initialize
    */
  def initializeVillage(grid: GridPane)

}

trait Setup {

  def configure(grid: GridPane): GridPane

}

object Setup {

  val setupVillage: Setup = {
    grid: GridPane => { // scorrere model

      val baseTile = tileSet.filter(t => t.description.equals("TERRAIN")).head

      loop(0, 10) foreach { // INVECE CHE GENERARE DA ZERO SCORRO IL MODEL E SETTO LE TILE CORRISPONDENTI
        case(x, y) =>
          val imageTile = new ImageView()
          imageTile.setImage(baseTile.imageSprite.snapshot(new SnapshotParameters, null))
//          addDragAndDropTargetHandler(imageTile)
//          addClickHandler(imageTile)
          grid add(imageTile, x, y)
      }

      def loop(s: Int, e: Int) =
      for(
        x <- s until e;
        y <- s until e
      ) yield (x, y)

      grid
    }
  }

  val setupBuildingsMenu: Setup = {
    grid: GridPane => {
      var pos = 0
      for(tile <- tileSet) {
        grid add(tile.imageSprite, 0, pos)
        addDragAndDropSourceHandler(tile)
        pos += 1
      }
      grid
    }
  }

  def addClickHandler(i: ImageView): Unit = { // ANDREBBE SPOSTATO FUORI E BISOGNA AGGIUNGERE COME PARAMETRO IL LUOGO DOVE MOSTRARE LE INFO
    i setOnMouseClicked(mouseEvent => {
      val y = GridPane.getColumnIndex(i)
      val x = GridPane.getRowIndex(i)
      println("Mouse clicked in coords: ("+x+","+y+")")
    })
  }

  def addDragAndDropSourceHandler(t: Tile): Unit = {
    val canvas = t.imageSprite
    canvas setOnDragDetected((event: MouseEvent) => {
      val dragBoard: Dragboard = canvas startDragAndDrop TransferMode.COPY
      val image = canvas.snapshot(new SnapshotParameters, null)
      dragBoard setDragView image
      val content: ClipboardContent = new ClipboardContent
      content putString t.description
      dragBoard setContent content
      event consume()
    })
  }

  def addDragAndDropTargetHandler(i: ImageView): Unit = {
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
      event consume()
      // UPDATE MODEL e send al server
    })
  }
}

object BaseGridInitializer extends GridInitializer {

  private def setupGrid(grid: GridPane, setup: Setup): Unit = {
    grid setHgap 10
    grid setVgap 10
    setup.configure(grid)
  }

  override def initializeBuildingsMenu(grid: GridPane): Unit = {
    setupGrid(grid, Setup.setupBuildingsMenu)
  }

  override def initializeVillage(grid: GridPane): Unit = {
    setupGrid(grid, Setup.setupVillage)
  }

}
