package cml.model.creatures

import cml.utils.ModelConfig.Creature.GOLEM_NAME
import cml.utils.ModelConfig.Creature.WATERDEMON_NAME
import cml.utils.ModelConfig.Creature.INITIAL_LEVEL
import cml.utils.ModelConfig.Elements.EARTH
import org.scalatest.FunSuite

/**
  * Test class for EarthCreature
  * @author Filippo Portolani
  */

class EarthCreatureTest extends FunSuite {

  val golemLevel = 9

  val golem: EarthCreature = EarthCreature(GOLEM_NAME, golemLevel)

  test("Level manipulation test"){
    golem levelUp()
    assert(golem.currentLevel > INITIAL_LEVEL)

    golem.level
    assert(golem.currentLevel == golemLevel)
  }

  test("Set attack test"){
    golem levelUp()
    assert(golem.attackValue == 20)
  }

  test("Element test"){
    assert(golem._element.equals(EARTH))
  }

  test("Name test"){
    assert(golem.name.equals(GOLEM_NAME))
    
    assert(golem.name != WATERDEMON_NAME)
  }

}
