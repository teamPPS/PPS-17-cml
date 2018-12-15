package cml.model.dynamic_model

import cml.model.base.Habitat.Habitat
import cml.model.base.{Cave, Farm, Position}
import cml.model.creatures.FireCreature
import cml.utils.ModelConfig.Elements.FIRE
import cml.utils.ModelConfig.Habitat.H_INIT_LEVEL
import cml.utils.ModelConfig.Creature.{DRAGON_NAME, INITIAL_LEVEL, DRAGON}
import cml.utils.{BuildingJson, CreatureJson}
import cml.utils.ModelConfig.Building.B_INIT_LEVEL
import cml.utils.ModelConfig.StructureType.{CAVE, FARM}
import org.scalatest.FunSuite

/**
  * @author Monica Gondolini
  */
class StructureUpgradeTest extends FunSuite {

  val x = 10
  val y = 10
  val pos = Position(x, y)

  val farm: Farm = Farm(pos, B_INIT_LEVEL)
  val prevFarmLevel: Int = farm.level
  val initFarmJson = BuildingJson(FARM, farm.level, farm.position)

  test("farm upgrade test"){
    StructureUpgrade(farm)
    assert(!farm.level.equals(prevFarmLevel))
  }

  test("farm upgrade in json test"){
    val json = StructureUpgrade(farm).structureJson
    assert(!json.equals(initFarmJson))
  }

  test("farm not having a creature json test"){
    val json = StructureUpgrade(farm).creatureJson
    assert(json == null)
  }

  val cave: Cave = Cave(pos, B_INIT_LEVEL)
  val prevCaveLevel: Int = cave.level
  val initCaveJson = BuildingJson(CAVE, cave.level, cave.position)

  test("cave upgrade test"){
    StructureUpgrade(cave)
    assert(!cave.level.equals(prevFarmLevel))
  }

  test("cave upgrade in json test"){
    val json = StructureUpgrade(cave).structureJson
    assert(!json.equals(initFarmJson))
  }

  test("cave not having a creature json test"){
    val json = StructureUpgrade(cave).creatureJson
    assert(json == null)
  }

  val habitat: Habitat = Habitat(FIRE, pos, H_INIT_LEVEL)
  val prevHabitatLevel: Int = habitat.level
  val initHabitatJson = BuildingJson(CAVE, habitat.level, habitat.position)

  val fireCreature = FireCreature(DRAGON_NAME, INITIAL_LEVEL)
  val prevCreatureLevel: Int = fireCreature.level
  val initCreatureJson = CreatureJson(DRAGON_NAME, INITIAL_LEVEL, DRAGON, habitat)
  habitat.creatures += fireCreature

  test("habitat and creature upgrade test"){
    StructureUpgrade(habitat)
    assert(!habitat.level.equals(prevFarmLevel) && !habitat.creatures.head.level.equals(prevCreatureLevel))
  }

  test("habitat and creature upgrade in json test"){
    val json = StructureUpgrade(habitat).structureJson
    val jsonCreature = StructureUpgrade(habitat).creatureJson
    assert(!json.equals(initFarmJson) && !jsonCreature.equals(initCreatureJson))
  }

  test("habitat having a creature json test"){
    val json = StructureUpgrade(habitat).creatureJson
    assert(json != null)
  }

}
