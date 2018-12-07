package cml.view.utils

import cml.utils.ModelConfig.Building.{B_INIT_LEVEL, TYPE_CAVE, TYPE_FARM}
import cml.utils.ModelConfig.Elements.{FIRE, WATER, AIR, EARTH}
import cml.utils.ModelConfig.StructureType.{WATER_HABITAT, FIRE_HABITAT, EARTH_HABITAT, AIR_HABITAT,FARM,CAVE}
import cml.model.base.Position
import cml.utils.{BuildingJson, HabitatJson}
import cml.view.{BaseTile, ImageSlicer, Tile}
import javafx.scene.image.Image

/**
  * @author ecavina
  */

object TileConfig {

  val spriteSheet: Image =  new Image(getClass.getClassLoader.getResource("image/Town64x64.png").toString, false)
  val slicer: ImageSlicer = new ImageSlicer(spriteSheet, 16, 16)
  val baseTileSet: Set[BaseTile] = Set[BaseTile](
    BaseTile("TERRAIN", slicer.sliceAt(6, 1))
  )
  val tileSet: Set[Tile] = Set[Tile](
    Tile(FIRE_HABITAT, slicer.sliceAt(4, 1), HabitatJson(FIRE, B_INIT_LEVEL, Position(0,0)).json),  //TODO dummy position?
    Tile(AIR_HABITAT, slicer.sliceAt(3, 1), HabitatJson(AIR, B_INIT_LEVEL, Position(0,0)).json),
    Tile(WATER_HABITAT, slicer.sliceAt(2, 1), HabitatJson(WATER, B_INIT_LEVEL, Position(0,0)).json),
    Tile(EARTH_HABITAT, slicer.sliceAt(1, 1), HabitatJson(EARTH, B_INIT_LEVEL, Position(0,0)).json),
    Tile(FARM, slicer.sliceAt(5, 1), BuildingJson(TYPE_FARM, B_INIT_LEVEL, Position(0,0)).json),
    Tile(CAVE, slicer.sliceAt(7, 1), BuildingJson(TYPE_CAVE, B_INIT_LEVEL, Position(0,0)).json)
  )
}
