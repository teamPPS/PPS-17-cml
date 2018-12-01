package cml.model.base

import cml.model.base.Habitat.SingleHabitat
import cml.model.creatures.Dragon
import cml.utils.ModelConfig.Building.{B_INIT_LEVEL, TYPE_FARM}
import cml.utils.ModelConfig.Elements.{AIR, WATER}
import org.scalatest.FunSuite

/**
  * @author Monica Gondolini
  */
class StructureTest extends FunSuite{

  test("Building level up test"){
    val building: Building = Building(TYPE_FARM, Position(10,10), B_INIT_LEVEL)
    building.levelUp()
    assert(building.buildingLevel > B_INIT_LEVEL)
  }

  test("Farm level up test"){
    val farm = Farm(Position(10,10), B_INIT_LEVEL)
    farm.levelUp()
    assert(farm.level > B_INIT_LEVEL)
  }

  test("Cave level up test"){
    val cave = Cave(Position(10,10), B_INIT_LEVEL)
    cave.levelUp()
    assert(cave.level > B_INIT_LEVEL)
  }


  test("Habitat level up test"){
//    val creatures: List[Creature] = List(Dragon("drago", 1))
    val habitat = Habitat(AIR,Position(100,100), B_INIT_LEVEL)
    val singleHabitat = SingleHabitat(WATER,Position(50,50), B_INIT_LEVEL, Dragon("drago2", 1))
    habitat levelUp()
    singleHabitat levelUp()
    assert(habitat.level > B_INIT_LEVEL && singleHabitat.level > B_INIT_LEVEL)
  }

  test("Get structure position test"){
    val pos = Position(10,10)
    val habitat = Habitat(AIR, pos, B_INIT_LEVEL)
    val building: Building = Building(TYPE_FARM, pos, B_INIT_LEVEL)
    assert(habitat.getPosition.equals(pos) && building.getPosition.equals(pos))
  }

}
