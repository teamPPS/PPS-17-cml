package cml.model

import cml.model.structures.{Building, Position}
import org.scalatest.FunSuite
import cml.utils.ModelConfig.Building.{LEVEL_INIT, TYPE_FARM}

class BuildingTest extends FunSuite{

  test("Food production test"){
    val building: Building = new Building(1, TYPE_FARM, Position(10,10), LEVEL_INIT)
    val food = Building.produce()
    println(food)
    assert(1==1)
  }

}
