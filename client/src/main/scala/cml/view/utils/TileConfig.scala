package cml.view.utils

import cml.view.{ImageSlicer, Tile}
import cml.schema.Village._
import javafx.scene.image.Image
import play.api.libs.json.{JsValue, Json}


object TileConfig {

  val spriteSheet: Image =  new Image(getClass.getClassLoader.getResource("image/Town64x64.png").toString, false)
  val slicer: ImageSlicer = new ImageSlicer(spriteSheet, 16, 16)
  val tileSet: Set[Tile] = Set[Tile](
    Tile("FARM", slicer.sliceAt(2, 3), buildingJson("Farm")),
    Tile("HABITAT", slicer.sliceAt(2, 3)),
    Tile("CAVE", slicer.sliceAt(2, 3), buildingJson("Cave")),
    Tile("TERRAIN", slicer.sliceAt(5, 3), buildingJson("Farm"))
  )

  def buildingJson(bType: String): JsValue = {
    val json: JsValue = Json.obj(
      "building_id" -> Json.obj(
        BUILDING_TYPE -> bType,
        BUILDING_LEVEL -> 1
      )
    )
    json
  }
}
