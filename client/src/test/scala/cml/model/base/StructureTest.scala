package cml.model.base

import cml.model.base.Habitat.SingleHabitat
import cml.utils.ModelConfig.Building.{LEVEL_INIT, TYPE_FARM}
import cml.utils.ModelConfig.Creature.INITIAL_LEVEL
import cml.utils.ModelConfig.Elements.{FIRE,WATER}
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
    val creatures: List[Creature] = List(Creature1("creatura", FIRE, INITIAL_LEVEL))
    val habitat = Habitat(FIRE,Position(100,100), LEVEL_INIT, creatures)
    val singleHabitat = SingleHabitat(WATER,Position(50,50), LEVEL_INIT, Creature1("creatura2", WATER, INITIAL_LEVEL))

    habitat levelUp()
    singleHabitat levelUp()
    assert(habitat.habitatLevel > LEVEL_INIT && singleHabitat.habitatLevel > LEVEL_INIT)
  }

}
