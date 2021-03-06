package cml.utils

import cml.model.base.{Position, Structure}
import cml.schema.Village._
import cml.utils.ModelConfig.Building.BUILDING
import cml.utils.ModelConfig.Habitat.HABITAT
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
case class BuildingJson(buildingType: String, buildingLevel: Int, buildingPosition: Position) extends JsonMaker {
  override def json: JsValue = Json.obj(
    buildingPosition.toString -> Json.obj(
      SINGLE_BUILDING_FIELD -> Json.obj(
        BUILDING_TYPE_FIELD -> buildingType,
        BUILDING_LEVEL_FIELD -> buildingLevel,
        BUILDING_POSITION_FIELD -> Json.obj("x" -> buildingPosition.x, "y" -> buildingPosition.y))))
}

/**
  * This class creates a new json for a Habitat by given fields
  * @param habitatElem natural element
  * @param habitatLevel level of the habitat
  */
case class HabitatJson(habitatElem: String, habitatLevel: Int, habitatPosition: Position) extends JsonMaker { //passare CreatureJson
  override def json: JsValue = Json.obj(
    habitatPosition.toString -> Json.obj(
      SINGLE_HABITAT_FIELD -> Json.obj(
        HABITAT_LEVEL_FIELD -> habitatLevel,
        NATURAL_ELEMENT_FIELD -> habitatElem,
        HABITAT_POSITION_FIELD -> Json.obj("x" -> habitatPosition.x, "y" -> habitatPosition.y))))
}

/**
  * This class creates a new json for a Creature by given fields
  * @param creatureName type of creature
  * @param creatureLevel level of the creature
  * @param creatureType: type of the creature
  * @param s structure the creature is living in
  */
case class CreatureJson(creatureName: String, creatureLevel: Int, creatureType: String, s: Structure) extends  JsonMaker {
  override def json: JsValue = Json.obj(
    s.position.toString -> Json.obj(
      SINGLE_HABITAT_FIELD -> Json.obj(
        HABITAT_LEVEL_FIELD -> s.level,
        NATURAL_ELEMENT_FIELD -> s.habitatElement,
        HABITAT_POSITION_FIELD -> Json.obj("x" -> s.position.x, "y" -> s.position.y),
        MULTIPLE_CREATURES_FIELD -> Json.obj(
          SINGLE_CREATURE_FIELD -> Json.obj(
            CREATURE_NAME_FIELD -> creatureName,
            CREATURE_LEVEL_FIELD -> creatureLevel,
            CREATURE_TYPE_FIELD -> creatureType)))))
}

/**
  * This class creates a new json for Food resource by given fields
  * @param amount of food
  */
case class FoodJson(amount: Int) extends JsonMaker {
  override def json: JsValue = Json.obj(FOOD_FIELD -> amount)
}

/**
  * This class creates a new json for Money resource by given fields
  * @param amount of money
  */
case class MoneyJson(amount: Int) extends JsonMaker {
  override def json: JsValue = Json.obj(GOLD_FIELD -> amount)
}

