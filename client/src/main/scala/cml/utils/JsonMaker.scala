package cml.utils

import cml.schema.Village._
import play.api.libs.json.{JsValue, Json}

/**
  * Trait to generate a new json
  * @author Monica Gondolini
  */
trait JsonMaker{
  def json: JsValue
}

/**
  * This class creates a new json for a building by given fields
  * @param buildingType type of building
  * @param buildingLevel level of the building
  */
case class BuildingJson(buildingType: String, buildingLevel: Int) extends JsonMaker {
  override def json: JsValue = Json.obj(
    BUILDINGS -> Json.obj(
      "building_id" -> Json.obj(
        BUILDING_TYPE -> buildingType,
        BUILDING_LEVEL -> buildingLevel
      )
    )
  )
}

/**
  * This class creates a new json for a Habitat by given fields
  * @param habitatElem natural element
  * @param habitatLevel level of the habitat
  */
case class HabitatJson(habitatElem: String, habitatLevel: Int) extends JsonMaker {
  override def json: JsValue = Json.obj(
    HABITAT -> Json.obj(
      "habitat_id" -> Json.obj(
        HABITAT_LEVEL -> habitatLevel,
        NATURAL_ELEMENT -> habitatElem
      )
    )
  )
}

/**
  * This class creates a new json for a Creature by given fields
  * @param creatureName type of creature
  * @param creatureLevel level of the creature
  */
case class CreatureJson(creatureName: String, creatureLevel: Int) extends  JsonMaker {
  override def json: JsValue = Json.obj(
    CREATURES -> Json.obj(
      CREATURE_NAME -> creatureName,
      CREATURE_LEVEL -> creatureLevel
    )
  )
}
