package cml.model.base

import cml.model.creatures.{FireCreature, EarthCreature, AirCreature, WaterCreature}
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

  val dragon : FireCreature = FireCreature(DRAGON_NAME, INITIAL_LEVEL)
  val dragon2 : FireCreature = FireCreature(DRAGON2_NAME, dragonLevel)
  val golem1 : EarthCreature = EarthCreature(GOLEM_NAME, INITIAL_LEVEL)
  val golem2 : EarthCreature = EarthCreature(GOLEM2_NAME, 10)
  val kraken: WaterCreature = WaterCreature(WATERDEMON_NAME, INITIAL_LEVEL)
  val griffin: AirCreature = AirCreature(GRIFFIN_NAME, griffinLevel)

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

  test("Add creature to habitat test"){
    val pos = Position(x, y)
    val habitat = Habitat(FIRE, pos, B_INIT_LEVEL)
    habitat.addCreature(dragon)
    assert(habitat.creatureList.head.equals(dragon))
  }

}
