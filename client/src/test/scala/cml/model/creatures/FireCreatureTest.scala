package cml.model.creatures

import cml.utils.ModelConfig.Creature.DRAGON_NAME
import cml.utils.ModelConfig.Creature.DRAGON2_NAME
import cml.utils.ModelConfig.Creature.INITIAL_LEVEL
import cml.utils.ModelConfig.Elements.FIRE
import org.scalatest.FunSuite

/**
  * Test class for FireCreature
  * @author Filippo Portolani
  */

class FireCreatureTest extends FunSuite {

  val dragonLevel = 10

  val dragon: FireCreature = FireCreature(DRAGON_NAME, dragonLevel)

  test("Level manipulation test"){
    dragon levelUp()
    assert(dragon.currentLevel > INITIAL_LEVEL)

    dragon.level
    assert(dragon.currentLevel == dragonLevel)
  }

  test("Set attack test"){
    dragon setAttack()
    assert(dragon.attackValue == 15)
  }

  test("Element test"){
    assert(dragon._element.equals(FIRE))
  }

  test("Name test"){
    assert(dragon.name.equals(DRAGON_NAME))

    assert(dragon.name != DRAGON2_NAME)
  }

}
