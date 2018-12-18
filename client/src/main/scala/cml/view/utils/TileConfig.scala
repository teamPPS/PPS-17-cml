package cml.view.utils

import cml.model.base.Position
import cml.utils.ModelConfig.Building.B_INIT_LEVEL
import cml.utils.ModelConfig.Elements.{AIR, EARTH, FIRE, WATER}
import cml.utils.ModelConfig.StructureType._
import cml.utils.{BuildingJson, HabitatJson}
import cml.view.{BaseTile, ImageSlicer, Tile}
import javafx.scene.image.Image

/**
  * Object utils for title configuration
  * @author ecavina
  */

object TileConfig {

  val spriteSheet: Image =  new Image(getClass.getClassLoader.getResource("image/Town64x64.png").toString, false)
  val slicer: ImageSlicer = new ImageSlicer(spriteSheet, 16, 16)
  val baseTileSet: Set[BaseTile] = Set[BaseTile](BaseTile("TERRAIN", slicer.sliceAt(6, 1)))
  val tileSet: Set[Tile] = Set[Tile](
    Tile(FIRE_HABITAT, slicer.sliceAt(4, 1), HabitatJson(FIRE, B_INIT_LEVEL, Position(0,0)).json),
    Tile(AIR_HABITAT, slicer.sliceAt(3, 1), HabitatJson(AIR, B_INIT_LEVEL, Position(0,0)).json),
    Tile(WATER_HABITAT, slicer.sliceAt(2, 1), HabitatJson(WATER, B_INIT_LEVEL, Position(0,0)).json),
    Tile(EARTH_HABITAT, slicer.sliceAt(1, 1), HabitatJson(EARTH, B_INIT_LEVEL, Position(0,0)).json),
    Tile(FARM, slicer.sliceAt(5, 1), BuildingJson(FARM, B_INIT_LEVEL, Position(0,0)).json),
    Tile(CAVE, slicer.sliceAt(7, 1), BuildingJson(FARM, B_INIT_LEVEL, Position(0,0)).json)
  )
}
