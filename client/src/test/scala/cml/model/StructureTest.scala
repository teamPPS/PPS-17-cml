package cml.model

import cml.model.base.Habitat.{Habitat, SingleHabitat}
import cml.model.base._
import org.scalatest.FunSuite
import cml.utils.ModelConfig.Building.{LEVEL_INIT, TYPE_FARM}
import cml.utils.ModelConfig.Elements.FIRE

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
    val creatures: List[String] = List("c1", "c2", "c3")
    val habitat = Habitat(FIRE,Position(100,100), LEVEL_INIT, creatures)
    val singleHabitat = SingleHabitat(FIRE,Position(50,50), LEVEL_INIT, "creatura")

    habitat levelUp()
    singleHabitat levelUp()
    assert(habitat.habitatLevel > LEVEL_INIT && singleHabitat.habitatLevel > LEVEL_INIT)
  }

}
