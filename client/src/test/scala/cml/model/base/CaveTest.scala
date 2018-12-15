package cml.model.base

import cml.utils.ModelConfig.Building.B_INIT_LEVEL
import cml.utils.ModelConfig.ModelClass.MONEY_CLASS
import org.scalatest.FunSuite

class CaveTest extends FunSuite{

  val x1 = 10
  val y1 = 10
  val pos = Position(x1, y1)
  val cave: Cave = Cave(pos, B_INIT_LEVEL)
  val amount = cave.money.amount

  test("cave level up test"){
    val prevLevel = cave.level
    cave.levelUp()
    assert(cave.level > prevLevel)
  }

  test("cave position test"){
    assert(cave.position.equals(pos))
  }

  test("cave resource production test") {
    for (i <- 1 to 10) {
      cave.money.inc(cave.building_level)
    }
    assert(cave.money.amount > amount)
  }

  test("cave resource retrievement test"){
    cave.money.take()
    assert(cave.money.amount.equals(amount))
  }

  test("cave resource type test"){
    assert(cave.resource.getClass.getName.equals(MONEY_CLASS))
  }

}
