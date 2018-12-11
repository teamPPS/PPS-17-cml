package cml.model.static_model

import cml.model.base.Creature
import cml.model.creatures.{FireCreature, EarthCreature, AirCreature, WaterCreature}
import cml.utils.ModelConfig.Creature._

/**
  * This object defines some static creatures
  * @author Filippo Portolani,Monica Gondolini
  */

object StaticCreaturesList {

  val creaturesList : List[Creature] = List(
    FireCreature(DRAGON_NAME,INITIAL_LEVEL),
    EarthCreature(GOLEM_NAME,INITIAL_LEVEL),
    AirCreature(GRIFFIN_NAME, INITIAL_LEVEL),
    WaterCreature(WATERDEMON_NAME,INITIAL_LEVEL)
  )

}
