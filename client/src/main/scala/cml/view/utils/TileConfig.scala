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
    Tile("FARM", slicer.sliceAt(2, 3), BuildingJson(TYPE_FARM, B_INIT_LEVEL).json),
    Tile("HABITAT", slicer.sliceAt(2, 3), HabitatJson(FIRE, B_INIT_LEVEL).json),
    Tile("CAVE", slicer.sliceAt(2, 3), BuildingJson(TYPE_CAVE, B_INIT_LEVEL).json),
    Tile("TERRAIN", slicer.sliceAt(5, 3), BuildingJson(TYPE_FARM, B_INIT_LEVEL).json) //da cambiare ovviamente
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
