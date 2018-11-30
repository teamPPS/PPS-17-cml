package cml.view.utils

import cml.view.{ImageSlicer, Tile}
import cml.schema.Village._
import cml.utils.ModelConfig.Habitat._
import cml.utils.ModelConfig.Building._
import cml.utils.ModelConfig.Elements._
import javafx.scene.image.Image
import play.api.libs.json.{JsValue, Json}


object TileConfig {

  val spriteSheet: Image =  new Image(getClass.getClassLoader.getResource("image/Town64x64.png").toString, false)
  val slicer: ImageSlicer = new ImageSlicer(spriteSheet, 16, 16)
  val tileSet: Set[Tile] = Set[Tile](
    Tile("FARM", slicer.sliceAt(2, 3), buildingJson(TYPE_FARM)),
    Tile("HABITAT", slicer.sliceAt(2, 3), habitatJson(FIRE)),
    Tile("CAVE", slicer.sliceAt(2, 3), buildingJson(TYPE_CAVE)),
    Tile("TERRAIN", slicer.sliceAt(5, 3), buildingJson(TYPE_FARM)) //da cambiare ovviamente
  )

  def buildingJson(bType: String): JsValue = {
    val json: JsValue = Json.obj(
      "building_id" -> Json.obj(
        BUILDING_TYPE -> bType,
        BUILDING_LEVEL -> B_INIT_LEVEL
      )
    )
    json
  }

  def habitatJson(element: String): JsValue = {
    val json: JsValue = Json.obj(
      "habitat_id" -> Json.obj(
        HABITAT_LEVEL -> H_INIT_LEVEL,
        NATURAL_ELEMENT -> element
      )
    )
    json
  }
}
