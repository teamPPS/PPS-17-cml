package cml.utils

import cml.schema.Village.{BUILDING_LEVEL, BUILDING_TYPE, HABITAT_LEVEL, NATURAL_ELEMENT}
import play.api.libs.json.{JsValue, Json}

trait JsonMaker{
  def json: JsValue
}

case class BuildingJson(buildingType: String, buildingLevel: Int) extends JsonMaker {
  override def json: JsValue = Json.obj(
    "building_id" -> Json.obj(
      BUILDING_TYPE -> buildingType,
      BUILDING_LEVEL -> buildingLevel
    )
  )
}

case class HabitatJson(habitatElem: String, habitatLevel: Int) extends JsonMaker {
  override def json: JsValue = Json.obj(
    "habitat_id" -> Json.obj(
      HABITAT_LEVEL -> habitatLevel,
      NATURAL_ELEMENT -> habitatElem
    )
  )
}
