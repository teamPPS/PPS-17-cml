package cml.model.dynamic_model

import java.util.{Timer, TimerTask}

import cml.model.base.{Cave, Farm, Habitat, Position}
import cml.utils.{FoodJson, MoneyJson}
import cml.utils.ModelConfig.Building.B_INIT_LEVEL
import cml.utils.ModelConfig.Habitat.H_INIT_LEVEL
import org.scalatest.FunSuite
import cml.utils.ModelConfig.Elements.WATER

/**
  * This is the test class for RetrieveResource
  * @author Filippo Portolani
  */

class RetrieveResourceTest extends FunSuite {

  val farm = Farm(Position(1,5), B_INIT_LEVEL)
  val initialFoodValue = farm.food.foodAmount
  val farmResourceType = RetrieveResource(farm).resourceType
  val initialFoodJson = FoodJson(initialFoodValue)

  val cave = Cave(Position(1,6), B_INIT_LEVEL)
  val initialMoneyValue = cave.money.moneyAmount
  val caveResourceType = RetrieveResource(cave).resourceType
  val initialMoneyJson = MoneyJson(initialMoneyValue)

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

  test("Retrieve farm food in json test"){
    val json = RetrieveResource(farm).resourceJson
    assert(!json.equals(initialFoodJson))
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

  test("Retrieve cave money in json test"){
    val json = RetrieveResource(cave).resourceJson
    assert(!json.equals(initialMoneyJson))
  }

  test("Retrieve habitat money test"){
    habitat.resource.inc(2)
    RetrieveResource(habitat)
    assert(habitat.resource.amount.equals(habitatInitialMoneyValue))
  }

  test("Retrieve habitat money in json test "){
    val json = RetrieveResource(habitat).resourceJson
    assert(!json.equals(initialMoneyJson))
  }

  test("Retrieving resources over time test"){
    val timer = new Timer()
    val task = new TimerTask {
      def run(): Unit = {
        farm.resource.inc(1)
        cave.resource.inc(1)
        habitat.resource.inc(1)
      }
    }
    val delay = 0
    val period = 1000L
    val millis = 3000
    timer.schedule(task, delay, period)
    Thread.sleep(millis)

    assert(farm.resource.amount > initialFoodValue && cave.resource.amount > initialMoneyValue && habitat.resource.amount > initialMoneyValue)
  }


}
