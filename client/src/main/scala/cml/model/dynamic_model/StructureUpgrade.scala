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
  s.levelUp()
  var jsonStructure: JsValue = _
  var jsonCreature: JsValue = _
  s.getClass.getName match {
    case FARM => //decrementare risorse globali + update
      jsonStructure = BuildingJson(FARM, s.level).json
    case CAVE => //decrementare risorse globali + update
      jsonStructure = BuildingJson(CAVE, s.level).json
    case HABITAT => //decrementare risorse globali cibo + denaro+ update
      jsonStructure = HabitatJson(FIRE, s.level).json
      //TODO cercare creatura all'interno della struttura
      jsonCreature = CreatureUpgrade(Dragon("Smaug", 2)).creatureJson
}

  println("Level up: $level \nfood-- \nmoney--") //da stampare in textarea livello

  override def structureJson: JsValue = jsonStructure

  override val creatureJson: JsValue = jsonCreature
}
