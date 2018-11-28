package cml.model.base

import cml.model.creatures.{Dragon, Golem}
import org.scalatest.FunSuite
import cml.utils.ModelConfig.Creature._

/**
  * Some tests for multiple creatures
  * @author Filippo Portolani
  */

class CreatureTest extends FunSuite {

  val dragon : Dragon = Dragon(DRAGON_NAME, INITIAL_LEVEL)
  val dragon2 : Dragon = Dragon("Saphira", 9)
  val golem : Golem = Golem(GOLEM_NAME, INITIAL_LEVEL)

  test("Dragon level up test"){
    dragon levelUp()
    assert(dragon.currentLevel > INITIAL_LEVEL)
  }

  test("Dragon set attack test"){
    dragon2.currentLevel_
    dragon2 levelUp()
    assert(dragon2.attackValue.equals(15))
  }

  test("Golem level up test"){
    golem levelUp()
    assert(golem.currentLevel > INITIAL_LEVEL)
  }

}
