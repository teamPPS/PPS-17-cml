package cml.model.static_model

import cml.model.base.Creature
import cml.model.creatures.{Creatures, Golem, Griffin, WaterDemon}
import cml.utils.ModelConfig.Creature._

/**
  * This object defines some static creatures
  * @author Filippo Portolani,Monica Gondolini
  */

object StaticCreaturesList {

  val creaturesList : List[Creature] = List(
    Creatures(DRAGON_NAME,INITIAL_LEVEL),
    Golem(GOLEM_NAME,INITIAL_LEVEL),
    Griffin(GRIFFIN_NAME, INITIAL_LEVEL),
    WaterDemon(WATERDEMON_NAME,INITIAL_LEVEL)
  )

}
