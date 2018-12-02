package cml.model.base

import cml.utils.ModelConfig.Building.B_INIT_LEVEL
import cml.utils.ModelConfig.Elements._
import org.scalatest.FunSuite

/**
  * @author Monica Gondolini
  */
class StructureTest extends FunSuite{

  test("Farm level up test"){
    val farm = Farm(Position(10,10), B_INIT_LEVEL)
    farm.levelUp()
    assert(farm.farmLevel > B_INIT_LEVEL)
  }

  test("Cave level up test"){
    val cave = Cave(Position(10,10), B_INIT_LEVEL)
    cave.levelUp()
    assert(cave.caveLevel > B_INIT_LEVEL)
  }


  test("Habitat level up test"){
    val habitat = Habitat(AIR,Position(100,100), B_INIT_LEVEL)
    habitat levelUp()
    assert(habitat.level > B_INIT_LEVEL)
  }

  test("Get structure position test"){
    val pos = Position(10,10)
    val farm = Farm(pos, B_INIT_LEVEL)
    val cave = Cave(pos, B_INIT_LEVEL)
    val habitat = Habitat(AIR, pos, B_INIT_LEVEL)

    assert(habitat.position.equals(pos) && cave.position.equals(pos) && farm.position.equals(pos))
  }


}
