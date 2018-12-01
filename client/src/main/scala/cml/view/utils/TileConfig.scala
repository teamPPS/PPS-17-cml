package cml.view.utils

import cml.utils.ModelConfig.Building._
import cml.utils.ModelConfig.Elements._
import cml.utils.{BuildingJson, HabitatJson}
import cml.view.{ImageSlicer, Tile}
import javafx.scene.image.Image


object TileConfig {

  val spriteSheet: Image =  new Image(getClass.getClassLoader.getResource("image/Town64x64.png").toString, false)
  val slicer: ImageSlicer = new ImageSlicer(spriteSheet, 16, 16)
  val tileSet: Set[Tile] = Set[Tile](
    Tile("FIRE_HABITAT", slicer.sliceAt(4, 1), HabitatJson(FIRE, B_INIT_LEVEL).json),
    Tile("AIR_HABITAT", slicer.sliceAt(3, 1), HabitatJson(AIR, B_INIT_LEVEL).json),
    Tile("WATER_HABITAT", slicer.sliceAt(2, 1), HabitatJson(WATER, B_INIT_LEVEL).json),
    Tile("EARTH_HABITAT", slicer.sliceAt(1, 1), HabitatJson(EARTH, B_INIT_LEVEL).json),
    Tile("FARM", slicer.sliceAt(5, 1), BuildingJson(TYPE_FARM, B_INIT_LEVEL).json),
    Tile("CAVE", slicer.sliceAt(7, 1), BuildingJson(TYPE_CAVE, B_INIT_LEVEL).json),
    Tile("TERRAIN", slicer.sliceAt(6, 1), BuildingJson(TYPE_FARM, B_INIT_LEVEL).json) //da cambiare ovviamente
  )

//  def buildingJson(bType: String): JsValue = {
//    val json: JsValue = Json.obj(
//      "building_id" -> Json.obj(
//        BUILDING_TYPE -> bType,
//        BUILDING_LEVEL -> B_INIT_LEVEL
//      )
//    )
//    json
//  }
//
//  def habitatJson(element: String): JsValue = {
//    val json: JsValue = Json.obj(
//      "habitat_id" -> Json.obj(
//        HABITAT_LEVEL -> H_INIT_LEVEL,
//        NATURAL_ELEMENT -> element
//      )
//    )
//    json
//  }
}
