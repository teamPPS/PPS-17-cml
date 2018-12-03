package cml.model.static_model

import cml.model.base.Creature
import cml.model.creatures.{Dragon, Golem}
import cml.utils.ModelConfig.Creature.{DRAGON_NAME, GOLEM_NAME, INITIAL_LEVEL}

/**
  * This object defines some static creatures
  * @author Filippo Portolani,Monica Gondolini
  */

object StaticCreaturesList {

  val creaturesList : List[Creature] = List(
    Dragon(DRAGON_NAME,INITIAL_LEVEL),
    Golem(GOLEM_NAME,INITIAL_LEVEL)
  )

}
