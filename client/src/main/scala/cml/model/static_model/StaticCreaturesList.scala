package cml.model.static_model

import cml.model.base.Creature
import cml.model.creatures.{Dragon, Golem, Griffin, Kraken}
import cml.utils.ModelConfig.Creature._

/**
  * This object defines some static creatures
  * @author Filippo Portolani,Monica Gondolini
  */

object StaticCreaturesList {

  val creaturesList : List[Creature] = List(
    Dragon(DRAGON_NAME,INITIAL_LEVEL),
    Golem(GOLEM_NAME,INITIAL_LEVEL),
    Griffin(GRIFFIN_NAME, INITIAL_LEVEL),
    Kraken(KRAKEN_NAME,INITIAL_LEVEL)
  )

}
