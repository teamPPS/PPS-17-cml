package cml.model.dynamic_model

import cml.model.base.Structure
import cml.utils.ModelConfig.ModelClass.{CAVE_CLASS, FARM_CLASS, HABITAT_CLASS}
import cml.utils.ModelConfig.StructureType.{CAVE,FARM}
import cml.utils.{BuildingJson, HabitatJson}
import play.api.libs.json.JsValue

/**
  * @author Monica Gondolini
  */
trait Upgrade extends UpgradeCreature {
  def structureJson: JsValue
}

/**
  * Upgrade a structure level
  * @param s structure to upgrade
  */
case class StructureUpgrade(s: Structure) extends Upgrade {

  private var jsonStructure: JsValue = _
  private var jsonCreature: JsValue = _

  s.levelUp()
  s.getClass.getName match {
    case FARM_CLASS => jsonStructure = BuildingJson(FARM, s.level, s.position).json
    case CAVE_CLASS => jsonStructure = BuildingJson(CAVE, s.level, s.position).json
    case HABITAT_CLASS =>
      if(s.creatures != null && s.creatures.nonEmpty){
        jsonStructure = HabitatJson(s.habitatElement, s.level, s.position).json
        jsonCreature = CreatureUpgrade(s.creatures.head).creatureJson
      }
  }

  override def structureJson: JsValue = jsonStructure
  override def creatureJson: JsValue = jsonCreature
}
