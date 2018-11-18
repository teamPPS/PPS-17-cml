package cml.view

import javafx.scene.SnapshotParameters
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.input._
import javafx.scene.layout.GridPane

trait GridInitializer {

  def initializeBuildingsMenu(grid: GridPane)
  def initializeVillage(grid: GridPane)

}

object BaseGridInitializer extends GridInitializer {

  val spriteSheet: Image =  new Image(getClass.getClassLoader.getResource("image/Town64x64.png").toString, false)

  val slicer: ImageSlicer = new ImageSlicer(spriteSheet, 16, 16)

  val tileSet: Set[Tile] = Set[Tile](
    Tile("FARM", slicer.sliceAt(2, 3)),
    Tile("HABITAT", slicer.sliceAt(2, 3)),
    Tile("CAVE", slicer.sliceAt(2, 3)),
    Tile("TERRAIN", slicer.sliceAt(5, 3))
  )

  override def initializeBuildingsMenu(grid: GridPane): Unit = {
    grid setHgap 10
    grid setVgap 10
    var pos = 0
    for(tile <- tileSet) {
      grid add(tile.imageSprite, 0, pos)
      addDragAndDropSourceHandler(tile)
      pos += 1
    }
  }

  override def initializeVillage(grid: GridPane): Unit = {
    grid setHgap 10
    grid setVgap 10
    val baseTile = tileSet.filter(t => t.description.equals("TERRAIN")).head
    loop(0, 10) foreach {
      case(x, y) =>
//        val terrainTile = Tile(baseTile.description, slicer.sliceAt(5, 3))
        val terrainTile = new Label(baseTile.description)
        addDragAndDropTargetHandler(terrainTile)
        grid add(terrainTile, x, y)
    }
  }

  private def loop(s: Int, e: Int) =
    for(
      x <- s until e;
      y <- s until e
    ) yield (x, y)

  // https://examples.javacodegeeks.com/desktop-java/javafx/event-javafx/javafx-drag-drop-example/
  def addDragAndDropSourceHandler(t: Tile): Unit = {

    val canvas = t.imageSprite
    canvas setOnDragDetected((event: MouseEvent) => {
      val dragBoard: Dragboard = canvas startDragAndDrop TransferMode.COPY
      val image = canvas.snapshot(new SnapshotParameters, null)
      dragBoard setDragView image
      val content: ClipboardContent = new ClipboardContent
      content putImage image
      content putString t.description
      dragBoard setContent content
      event consume()
    })

  }

  def addDragAndDropTargetHandler(t: Label): Unit = {

    t setOnDragOver((event: DragEvent) => {
      event acceptTransferModes TransferMode.COPY
      event consume()
    })

    t setOnDragDropped((event: DragEvent) => {
      val dragBoard: Dragboard = event getDragboard()
//      val newImage: Image = dragBoard.getImage
//      image.getGraphicsContext2D.drawImage(newImage, 0, 0)
      t setText dragBoard.getString
      event consume()
    })
  }

}
