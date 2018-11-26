package cml.model.static_model

import cml.model.base.Creature
import cml.model.creatures.{Dragon, Golem}
import cml.utils.ModelConfig.Creature.{DRAGON_NAME, GOLEM_NAME, INITIAL_LEVEL}

object StaticCreatures {

  val creaturesList : List[Creature] = List(
    Dragon(DRAGON_NAME,INITIAL_LEVEL),
    Golem(GOLEM_NAME,INITIAL_LEVEL)
  )

}
