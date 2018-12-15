package cml.model.dynamic_model

import cml.model.base.{Cave, Farm, Habitat, Position}
import cml.utils.ModelConfig.Building.B_INIT_LEVEL
import cml.utils.ModelConfig.Habitat.H_INIT_LEVEL
import org.scalatest.FunSuite
import cml.utils.ModelConfig.Elements.WATER

/**
  * @author Filippo Portolani
  */

class RetrieveResourceTest extends FunSuite {

  val farm = Farm(Position(1,5), B_INIT_LEVEL)
  val initialFoodValue = farm.food.foodAmount
  val farmResourceType = RetrieveResource(farm).resourceType

  val cave = Cave(Position(1,6), B_INIT_LEVEL)
  val initialMoneyValue = cave.money.moneyAmount
  val caveResourceType = RetrieveResource(cave).resourceType

  val habitat = Habitat(WATER, Position(1,7), H_INIT_LEVEL)
  val habitatInitialMoneyValue = habitat.money.moneyAmount


  test("Retrieve farm food test"){
    farm.resource.inc(2)
    RetrieveResource(farm)
    farm.resource.inc(1)
    assert(farm.resource.amount > initialFoodValue)
  }

  test("Farm resource type test"){
    assert(farmResourceType.equals("Food"))
  }

  test("Retrieve cave money test"){
    cave.resource.inc(2)
    RetrieveResource(cave)
    cave.resource.inc(1)
    assert(initialMoneyValue < cave.resource.amount)
  }

  test("Cave resource type test"){
    assert(caveResourceType.equals("Money"))
  }

  test("Retrieve habitat money test"){
    habitat.resource.inc(2)
    RetrieveResource(habitat)
    assert(habitat.resource.amount.equals(habitatInitialMoneyValue))
  }


}
