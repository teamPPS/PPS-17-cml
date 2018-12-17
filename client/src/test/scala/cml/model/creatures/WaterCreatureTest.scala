package cml.model.creatures

import cml.utils.ModelConfig.Creature.WATERDEMON_NAME
import cml.utils.ModelConfig.Creature.GOLEM2_NAME
import cml.utils.ModelConfig.Creature.INITIAL_LEVEL
import cml.utils.ModelConfig.Elements.WATER
import org.scalatest.FunSuite

/**
  * Test class for WaterCreature
  * @author Filippo Portolani
  */

class WaterCreatureTest extends FunSuite {

  val waterDemonLevel = 10

  val waterDemon: WaterCreature = WaterCreature(WATERDEMON_NAME, waterDemonLevel)

  test("Level manipulation test"){
    //level up test
    waterDemon levelUp()
    assert(waterDemon.currentLevel > INITIAL_LEVEL)

    //Setting level test
    waterDemon.level
    assert(waterDemon.currentLevel == waterDemonLevel)
  }

  test("Set attack test"){
    waterDemon setAttack()
    assert(waterDemon.attackValue == 15)
  }

  test("Element test"){
    assert(waterDemon._element.equals(WATER))
  }

  test("Name test"){
    //name is correct
    assert(waterDemon.name.equals(WATERDEMON_NAME))

    //name is wrong
    assert(waterDemon.name != GOLEM2_NAME)
  }

}
