package cml.model.creatures

import cml.utils.ModelConfig.Creature.GRIFFIN_NAME
import cml.utils.ModelConfig.Creature.GOLEM_NAME
import cml.utils.ModelConfig.Creature.INITIAL_LEVEL
import cml.utils.ModelConfig.Elements.AIR
import org.scalatest.FunSuite

/**
  * This class tests AirCreature class
  * @author Filippo Portolani
  */

class AirCreatureTest extends FunSuite {

  val griffinLevel = 8

  val griffin: AirCreature = AirCreature(GRIFFIN_NAME, griffinLevel)

  test("Level manipulation test"){

    griffin levelUp()
    assert(griffin.currentLevel > INITIAL_LEVEL)


    griffin.level
    assert(griffin.currentLevel == griffinLevel)
  }

  test("Set attack test"){
    griffin levelUp()
    griffin levelUp()
    assert(griffin.attackValue == 15)
  }

  test("Element test"){
    assert(griffin._element.equals(AIR))
  }

  test("Name test"){

    assert(griffin.name.equals(GRIFFIN_NAME))

    assert(griffin.name != GOLEM_NAME)
  }

}
