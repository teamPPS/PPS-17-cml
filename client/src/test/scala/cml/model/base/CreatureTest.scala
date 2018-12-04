package cml.model.base

import cml.model.creatures.{Dragon, Golem, Griffin, Kraken}
import org.scalatest.FunSuite
import cml.utils.ModelConfig.Creature._

/**
  * Some tests for multiple creatures
  * @author Filippo Portolani
  */

class CreatureTest extends FunSuite {

  val dragon : Dragon = Dragon(DRAGON_NAME, INITIAL_LEVEL)
  val dragon2 : Dragon = Dragon("Saphira", 9)
  val golem1 : Golem = Golem(GOLEM_NAME, INITIAL_LEVEL)
  val golem2 : Golem = Golem("Alduin", 10)
  val kraken: Kraken = Kraken(KRAKEN_NAME, INITIAL_LEVEL)
  val griffin: Griffin = Griffin(GRIFFIN_NAME, 8)

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
    golem1 levelUp()
    assert(golem1.currentLevel > INITIAL_LEVEL)
  }

  test("Golem get element test"){
    assert(golem1.element.equals("earth"))
  }

  test("Golem set level and get current attack power test"){
    golem2.currentLevel_
    assert(golem2.attackPower.equals(20))
  }

  test("Kraken get element test"){
    assert(kraken.element.equals("water"))
  }

  test("Griffin set level test"){
    griffin.currentLevel_
    assert(griffin.currentLevel.equals(8))

  }
}
