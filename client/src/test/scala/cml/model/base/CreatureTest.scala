package cml.model.base

import cml.model.creatures.{Dragon, Golem}
import org.scalatest.FunSuite
import cml.utils.ModelConfig.Creature.INITIAL_LEVEL

/**
  * Some tests for multiple creatures
  * @author Filippo Portolani
  */

class CreatureTest extends FunSuite {

  val dragon : Dragon = Dragon("Smaug", INITIAL_LEVEL)
  val dragon2 : Dragon = Dragon("Saphira", 9)
  val golem : Golem = Golem("Astaroth", INITIAL_LEVEL)

  test("Dragon level up test"){
    dragon levelUp()
    assert(dragon.currentLevel > INITIAL_LEVEL)
  }

  test("Dragon set attack test"){
    dragon2 setLevel()
    dragon2 levelUp()
    dragon2 setAttack()
    assert(dragon2.attackValue.equals(15))
  }

  test("Golem level up test"){
    golem levelUp()
    assert(golem.currentLevel > INITIAL_LEVEL)
  }

}
