package cml.model.dynamic_model

import cml.controller.fx.VillageViewController
import cml.controller.messages.VillageRequest.UpdateVillage
import cml.model.base.Structure
import cml.utils.{BuildingJson, HabitatJson}
import cml.utils.ModelConfig.Elements.FIRE
import cml.utils.ModelConfig.ModelClass.{CAVE, FARM, HABITAT}
import cml.view.Handler.villageActor

case class StructureUpgrade(s: Structure, c: VillageViewController) {
  s.levelUp()
  s.getClass.getName match {
    case FARM => //decrementare risorse globali + update
      val json = BuildingJson(FARM, s.level).json
      villageActor ! UpdateVillage(json)
    case CAVE => //decrementare risorse globali + update
      val json = BuildingJson(CAVE, s.level).json
      villageActor ! UpdateVillage(json)
    case HABITAT => //decrementare risorse globali cibo + denaro+ update
      val jsonHabitat = HabitatJson(FIRE, s.level).json
      villageActor ! UpdateVillage(jsonHabitat)
    //creature json aumento livello creatura
    //                    val jsonCreature = CreatureJson()
    //                    villageActor ! UpdateVillage(jsonCreature)
  }
  println("Level up: $level \nfood-- \nmoney--") //da stampare in textarea livello
  c.levelUpButton setDisable true
}
