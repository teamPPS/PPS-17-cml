package cml.model.base

import cml.model.base.Habitat.SingleHabitat
import cml.model.creatures.Dragon
import cml.utils.ModelConfig.Building.{LEVEL_INIT, TYPE_FARM}
import cml.utils.ModelConfig.Elements.{AIR, WATER}
import org.scalatest.FunSuite

/**
  * @author Monica Gondolini
  */
class StructureTest extends FunSuite{

  test("Building level up test"){
    val building: Building = Building(TYPE_FARM, Position(10,10), LEVEL_INIT)
    building levelUp()
    assert(building.buildingLevel > LEVEL_INIT)
  }


  test("Habitat level up test"){
    val creatures: List[Creature] = List(Dragon("drago", AIR))
    val habitat = Habitat(AIR,Position(100,100), LEVEL_INIT, creatures)
    val singleHabitat = SingleHabitat(WATER,Position(50,50), LEVEL_INIT, Dragon("drago2", AIR))

    habitat levelUp()
    singleHabitat levelUp()
    assert(habitat.habitatLevel > LEVEL_INIT && singleHabitat.habitatLevel > LEVEL_INIT)
  }

}
