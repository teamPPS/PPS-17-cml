package cml.model.base

import cml.model.creatures.{Dragon, Golem}
import cml.utils.ModelConfig.Building.B_INIT_LEVEL
import org.scalatest.FunSuite
import cml.utils.ModelConfig.Creature._
import cml.utils.ModelConfig.Elements.FIRE

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
    dragon2 setAttack()
    assert(dragon2.attackValue.equals(15))
  }

  test("Golem level up test"){
    golem levelUp()
    assert(golem.currentLevel > INITIAL_LEVEL)
  }

  test("Add creature to habitat test"){
    val pos = Position(10,10)
    val habitat = Habitat(FIRE, pos, B_INIT_LEVEL)
    habitat.addCreature(dragon)
    assert(habitat.creatures.head.equals(dragon))
  }

}
