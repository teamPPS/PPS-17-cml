package cml.model.dynamic_model

import cml.model.base.Structure
import cml.utils.ModelConfig.Elements.FIRE
import cml.utils.ModelConfig.ModelClass.{CAVE, FARM, HABITAT}
import cml.utils.{BuildingJson, HabitatJson}
import play.api.libs.json.JsValue

/**
  * @author Monica Gondolini
  */
trait Upgrade {
  def json: JsValue
}

/**
  * Upgrade a structure level
  * @param s structure to upgrade
  */
case class StructureUpgrade(s: Structure) extends Upgrade {
  s.levelUp()
  var structJson: JsValue = _
  s.getClass.getName match {
    case FARM => //decrementare risorse globali + update
      structJson = BuildingJson(FARM, s.level).json
    case CAVE => //decrementare risorse globali + update
      structJson = BuildingJson(CAVE, s.level).json
    case HABITAT => //decrementare risorse globali cibo + denaro+ update
      structJson = HabitatJson(FIRE, s.level).json
    //creature json aumento livello creatura
    //                    val jsonCreature = CreatureJson()
    //                    villageActor ! UpdateVillage(jsonCreature)
}
  println("Level up: $level \nfood-- \nmoney--") //da stampare in textarea livello

  override def json: JsValue = {
    println(structJson)
    structJson
  }
}
