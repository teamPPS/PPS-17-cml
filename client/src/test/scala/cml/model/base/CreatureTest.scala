package cml.model.base

import cml.model.creatures.{Dragon, Golem, Griffin, Kraken}
import cml.utils.ModelConfig.Building.B_INIT_LEVEL
import org.scalatest.FunSuite
import cml.utils.ModelConfig.Creature._
import cml.utils.ModelConfig.Elements.FIRE

/**
  * Some tests for multiple creatures
  * @author Filippo Portolani
  */

class CreatureTest extends FunSuite {

  val dragonLevel = 9
  val griffinLevel = 8
  val attackDragon = 15

  val x = 10
  val y = 10

  val dragon : Dragon = Dragon(DRAGON_NAME, INITIAL_LEVEL)
  val dragon2 : Dragon = Dragon("Saphira", dragonLevel)
  val golem : Golem = Golem(GOLEM_NAME, INITIAL_LEVEL)
  val kraken: Kraken = Kraken(KRAKEN_NAME, INITIAL_LEVEL)
  val griffin: Griffin = Griffin(GRIFFIN_NAME, griffinLevel)

  test("Dragon level up test"){
    dragon levelUp()
    assert(dragon.currentLevel > INITIAL_LEVEL)
  }

  test("Dragon set attack test"){
    dragon2.currentLevel_
    dragon2 levelUp()
    assert(dragon2.attackValue.equals(attackDragon))
  }

  test("Golem level up test"){
    golem levelUp()
    assert(golem.currentLevel > INITIAL_LEVEL)
  }

  test("Kraken get element test"){
    assert(kraken.element == "water")
  }

  test("Griffin set level test"){
    griffin.currentLevel_
    assert(griffin.currentLevel == 8)

  }

  test("Add creature to habitat test"){
    val pos = Position(x, y)
    val habitat = Habitat(FIRE, pos, B_INIT_LEVEL)
    habitat.addCreature(dragon)
    assert(habitat.creatureList.head.equals(dragon))
  }

}
