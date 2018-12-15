package cml.model.base

import cml.utils.ModelConfig.Building.B_INIT_LEVEL
import cml.utils.ModelConfig.ModelClass.FOOD_CLASS
import org.scalatest.FunSuite

/**
  * @author Monica Gondolini
  */

class FarmTest extends FunSuite{

  val x = 10
  val y = 10
  val pos = Position(x, y)
  val farm: Farm = Farm(pos, B_INIT_LEVEL)
  val amount = farm.food.amount

  test("farm level up test"){
    val prevLevel = farm.level
    farm.levelUp()
    assert(farm.level > prevLevel)
  }

  test("farm position test"){
    assert(farm.position.equals(pos))
  }

  test("farm resource production test") {
    for (i <- 1 to 10) {
      farm.food.inc(farm.building_level)
    }
    assert(farm.food.amount > amount)
  }

  test("farm resource retrievement test"){
    farm.food.take()
    assert(farm.food.amount.equals(amount))
  }

  test("farm resource type test"){
    assert(farm.resource.getClass.getName.equals(FOOD_CLASS))
  }

}
