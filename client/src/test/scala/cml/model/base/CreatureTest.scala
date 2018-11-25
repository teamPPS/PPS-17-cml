package cml.model.base

import cml.model.creatures.Dragon
import org.scalatest.FunSuite
import cml.utils.ModelConfig.Elements.AIR
import cml.utils.ModelConfig.Creature.INITIAL_LEVEL

/**
  * Some tests for multiple creatures
  * @author Filippo Portolani
  */

class CreatureTest extends FunSuite {

  test("Creature level up test"){
    val creature: Dragon = Dragon("Smaug", AIR, 4)
    creature.levelUp()
    assert(creature.currentLevel > INITIAL_LEVEL)
  }

}
