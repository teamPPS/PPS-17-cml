package cml.model.base

import cml.model.base.Habitat.Habitat
import cml.utils.ModelConfig.Building.B_INIT_LEVEL
import cml.utils.ModelConfig.Habitat.H_INIT_LEVEL
import cml.utils.ModelConfig.Elements.FIRE
import org.scalatest.FunSuite

import scala.collection.mutable

/**
  * @author Monica Gondolini
  */
class VillageMapTest extends FunSuite{

  val defGold = 0
  val defFood = 0
  val defUsername = "default"
  val structures: mutable.MutableList[Structure] = mutable.MutableList[Structure]()
  val defaultVillage = VillageMap(structures, defGold, defFood, defUsername)

  /*test("Empty village instance"){
    VillageMap.instance()
    assert(VillageMap.instance().get.equals(defaultVillage))
  }*/

  test("Fill villageMap instance"){
    val x = 10
    val y = 10
    val pos = Position(x, y)

    val gold = 100
    val food = 100
    val username = "john"

    val cave: Cave = Cave(pos, B_INIT_LEVEL)
    structures += cave

    val habitat: Habitat = Habitat(FIRE, pos, H_INIT_LEVEL)
    structures += habitat

    VillageMap.initVillage(structures, gold, food, username)

    assert(!VillageMap.instance().get.equals(defaultVillage))
  }

}
