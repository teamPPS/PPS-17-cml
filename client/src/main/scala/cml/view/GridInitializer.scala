package cml.view

import javafx.scene.SnapshotParameters
import javafx.scene.image.{Image, ImageView}
import javafx.scene.input._
import javafx.scene.layout.GridPane

/**
  * Initialize Game GridPanes with style classes
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

  // LA PARTE DELLE TILE VA PORTATA FUORI, E' USATA IN PIU LUOGHI
  val spriteSheet: Image =  new Image(getClass.getClassLoader.getResource("image/Town64x64.png").toString, false)
  val slicer: ImageSlicer = new ImageSlicer(spriteSheet, 16, 16)
  val tileSet: Set[Tile] = Set[Tile](
    Tile("FARM", slicer.sliceAt(2, 3)),
    Tile("HABITAT", slicer.sliceAt(2, 3)),
    Tile("CAVE", slicer.sliceAt(2, 3)),
    Tile("TERRAIN", slicer.sliceAt(5, 3))
  )


  val setupVillage: Setup = {
    grid: GridPane => { // scorrere model

      //aggiungere handler per click sinistro per mostrare informazioni sulla gui

      val baseTile = tileSet.filter(t => t.description.equals("TERRAIN")).head

      loop(0, 10) foreach { // INVECE CHE GENERARE DA ZERO SCORRO IL MODEL E SETTO LE TILE CORRISPONDENTI
        case(x, y) =>
          val imageTile = new ImageView()
          imageTile.setImage(baseTile.imageSprite.snapshot(new SnapshotParameters, null))
          addDragAndDropTargetHandler(imageTile)
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

  def addDragAndDropTargetHandler(c: ImageView): Unit = {
    c setOnDragOver ((event: DragEvent) => {
      event acceptTransferModes TransferMode.COPY
      event consume()
    })

    c setOnDragDropped ((event: DragEvent) => {
      val dragBoard: Dragboard = event getDragboard()
      val newTile = tileSet.filter(t => t.description.equals(dragBoard.getString)).head
      c setImage newTile.imageSprite.snapshot(new SnapshotParameters, null)
      val y = GridPane.getColumnIndex(c)
      val x = GridPane.getRowIndex(c)
      println("Dropped element " + dragBoard.getString + " in coordinates (" + x + " - " + y + ")")
      event consume()
      // UPDATE MODEL senza(???) modificare la GUI e send al server
      // se ??? TODO aggiungere engine che renderizza il model abbastanza rapido da fare sembrare all'utente che droppando aggiunge l'elemento
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
