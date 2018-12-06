package cml.model.dynamic_model

import cml.model.base.Structure
import cml.model.creatures.Dragon
import cml.utils.ModelConfig.Elements.FIRE
import cml.utils.ModelConfig.ModelClass.{CAVE, FARM, HABITAT}
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
    case FARM => jsonStructure = BuildingJson(FARM, s.level, s.position).json
    case CAVE => jsonStructure = BuildingJson(CAVE, s.level, s.position).json
    case HABITAT => jsonStructure = HabitatJson(FIRE, s.level, s.position).json
      s.addCreature(Dragon("Smaug", 1))
      if(s.creatures != null) jsonCreature = CreatureUpgrade(s.creatures.head).creatureJson
  }

  override def structureJson: JsValue = jsonStructure
  override def creatureJson: JsValue = jsonCreature
}
