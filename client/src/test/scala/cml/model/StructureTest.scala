package cml.model

import cml.model.base._
import org.scalatest.FunSuite
import cml.utils.ModelConfig.Building.{LEVEL_INIT, TYPE_FARM}

/**
  * @author Monica Gondolini
  */
class StructureTest extends FunSuite{

  test("Structure level up test"){
    val building: Building = Building(TYPE_FARM, Position(10,10), LEVEL_INIT)
    building.levelUp()
    println(building.food.inc(), building.food.value)
    println(building.buildingLevel)
  }

}
