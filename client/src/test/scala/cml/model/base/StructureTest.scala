package cml.model.base

import cml.utils.ModelConfig.Building.B_INIT_LEVEL
import cml.utils.ModelConfig.Elements.AIR
import org.scalatest.FunSuite

/**
  * This test class matches if Structure class is correct
  * @author Monica Gondolini
  */
class StructureTest extends FunSuite{

  val x = 10
  val y = 10

  test("Farm level up test"){
    val farm = Farm(Position(x, y), B_INIT_LEVEL)
    farm.levelUp()
    assert(farm.building_level > B_INIT_LEVEL)
  }

  test("Cave level up test"){
    val cave = Cave(Position(x, y), B_INIT_LEVEL)
    cave.levelUp()
    assert(cave.building_level > B_INIT_LEVEL)
  }

  test("Habitat level up test"){
    val habitat = Habitat(AIR,Position(x, y), B_INIT_LEVEL)
    habitat levelUp()
    assert(habitat.level > B_INIT_LEVEL)
  }

  test("Get structure position test"){
    val pos = Position(x, y)
    val farm = Farm(pos, B_INIT_LEVEL)
    val cave = Cave(pos, B_INIT_LEVEL)
    val habitat = Habitat(AIR, pos, B_INIT_LEVEL)

    assert(habitat.position.equals(pos) && cave.position.equals(pos) && farm.position.equals(pos))
  }

}
