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

  var jsonStructure: JsValue = _
  var jsonCreature: JsValue = _

  s.levelUp()
  s.getClass.getName match {
    case FARM => //decrementare risorse globali + update
      jsonStructure = BuildingJson(FARM, s.level).json
    case CAVE => //decrementare risorse globali + update
      jsonStructure = BuildingJson(CAVE, s.level).json
    case HABITAT => //decrementare risorse globali cibo + denaro+ update
      jsonStructure = HabitatJson(FIRE, s.level).json
      s.addCreature(Dragon("Smaug", 1))
      if(s.creatures != null) jsonCreature = CreatureUpgrade(s.creatures.head).creatureJson
  }

  println("Level up: $level \nfood-- \nmoney--") //da stampare in textarea livello

  override def structureJson: JsValue = jsonStructure
  override def creatureJson: JsValue = jsonCreature
}
