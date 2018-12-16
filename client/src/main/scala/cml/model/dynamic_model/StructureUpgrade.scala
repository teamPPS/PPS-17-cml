package cml.model.dynamic_model

import cml.model.base.Structure
import cml.utils.ModelConfig.ModelClass.{CAVE_CLASS, FARM_CLASS, HABITAT_CLASS}
import cml.utils.ModelConfig.StructureType.{CAVE, FARM}
import cml.utils.BuildingJson
import play.api.libs.json.JsValue

/**
  * @author Monica Gondolini
  */
trait Upgrade extends UpgradeCreature {
  def structureJson: JsValue
  def positionJson: JsValue
}

/**
  * Upgrade a structure level
  * @param s structure to upgrade
  */
case class StructureUpgrade(s: Structure) extends Upgrade {

  private var jsonStructure: JsValue = _
  private var jsonCreature: Option[JsValue] = _
  private var jsonPosition: JsValue = _

  s.levelUp()
  s.getClass.getName match {
    case FARM_CLASS =>
      jsonStructure = BuildingJson(FARM, s.level, s.position).json
    case CAVE_CLASS =>
      jsonStructure = BuildingJson(CAVE, s.level, s.position).json
    case HABITAT_CLASS =>
      if(s.creatures.nonEmpty){
        jsonCreature = CreatureUpgrade(s.creatures.get.head, s).creatureJson
      } else jsonCreature = Option.empty
  }

  override def structureJson: JsValue = jsonStructure
  override def creatureJson: Option[JsValue] = jsonCreature
  override def positionJson: JsValue = jsonPosition
}
