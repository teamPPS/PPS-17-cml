package cml.model.static_model

import cml.utils.ModelConfig.Building._
import cml.utils.ModelConfig.Elements._
import cml.utils.ModelConfig.Habitat._

/**
  * This object defines some static structures
  * @author Filippo Portolani,Monica Gondolini
  */

sealed trait StaticStructures

class StaticBuilding(bType: String, bLevel: Int) extends StaticStructures

class StaticHabitat(elem: String, hLevel: Int) extends StaticStructures

object StaticStructures{

  val staticHabitats : List[StaticHabitat] = List(
    new StaticHabitat(AIR, H_INIT_LEVEL),
    new StaticHabitat(FIRE, H_INIT_LEVEL),
    new StaticHabitat(WATER, H_INIT_LEVEL),
    new StaticHabitat(EARTH, H_INIT_LEVEL)
  )

  val staticBuildings : List[StaticBuilding] = List(
    new StaticBuilding(TYPE_FARM, B_INIT_LEVEL),
    new StaticBuilding(TYPE_CAVE, B_INIT_LEVEL)
  )
}