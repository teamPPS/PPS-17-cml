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
trait Upgrade extends UpgradeCreature{
  def structureJson: JsValue
}

/**
  * Upgrade a structure level
  * @param s structure to upgrade
  */
case class StructureUpgrade(s: Structure) extends Upgrade {
  s.levelUp()
  var json: JsValue = _
  var cJson: JsValue = _
  s.getClass.getName match {
    case FARM => //decrementare risorse globali + update
      json = BuildingJson(FARM, s.level).json
    case CAVE => //decrementare risorse globali + update
      json = BuildingJson(CAVE, s.level).json
    case HABITAT => //decrementare risorse globali cibo + denaro+ update
      json = HabitatJson(FIRE, s.level).json
    //creature json aumento livello creatura
      cJson = CreatureUpgrade(Dragon("ciccio", 2)).creatureJson
//                    val jsonCreature = CreatureJson()
//                    villageActor ! UpdateVillage(jsonCreature)
}
  println("Level up: $level \nfood-- \nmoney--") //da stampare in textarea livello

  override def structureJson: JsValue = {
    println(json)
    json
  }

  override val creatureJson: JsValue = cJson
}
