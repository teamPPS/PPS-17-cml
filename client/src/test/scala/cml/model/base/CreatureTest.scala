package cml.model.base

import org.scalatest.FunSuite
import cml.utils.ModelConfig.Elements.FIRE
import cml.utils.ModelConfig.Creature.INITIAL_LEVEL

class CreatureTest extends FunSuite {

  test("Creature level up test"){
    val creature: Creature1 = Creature1("Dragon", FIRE, INITIAL_LEVEL,Position(20,20))
    creature.levelUp()
    assert(creature.creatureLevel > INITIAL_LEVEL)
  }

}
