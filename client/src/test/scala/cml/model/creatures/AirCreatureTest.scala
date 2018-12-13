package cml.model.creatures

import cml.utils.ModelConfig.Creature.GRIFFIN_NAME
import cml.utils.ModelConfig.Creature.INITIAL_LEVEL
import org.scalatest.FunSuite

/**
  * This class tests AirCreature class
  * @author Filippo Portolani
  */

class AirCreatureTest extends FunSuite {

  val griffinLevel = 8

  val griffin: AirCreature = AirCreature(GRIFFIN_NAME, griffinLevel)

  test("Level manipulation test"){
    //level up test
    griffin levelUp()
    assert(griffin.currentLevel > INITIAL_LEVEL)

    //Setting level test
    griffin.level
    assert(griffin.currentLevel == griffinLevel)
  }

  test("Set attack test"){ //griffin level is incremented twice so its attack power is now 15
    griffin levelUp()
    griffin levelUp()
    assert(griffin.attackValue == 15)
  }

  test("Element test"){
    assert(griffin._element.equals("air"))
  }

  

}
