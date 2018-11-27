package cml.view.utils

import cml.view.{ImageSlicer, Tile}
import javafx.scene.image.Image

object TileConfig {

  val spriteSheet: Image =  new Image(getClass.getClassLoader.getResource("image/Town64x64.png").toString, false)
  val slicer: ImageSlicer = new ImageSlicer(spriteSheet, 16, 16)
  val tileSet: Set[Tile] = Set[Tile](
    Tile("FARM", slicer.sliceAt(2, 3)),
    Tile("HABITAT", slicer.sliceAt(2, 3)),
    Tile("CAVE", slicer.sliceAt(2, 3)),
    Tile("TERRAIN", slicer.sliceAt(5, 3))
  )

}
