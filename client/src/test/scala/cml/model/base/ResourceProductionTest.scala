package cml.model.base

import java.util.{Timer, TimerTask}

import cml.utils.ModelConfig.Building.B_INIT_LEVEL
import cml.utils.ModelConfig.Elements.AIR
import cml.utils.ModelConfig.Habitat.H_INIT_LEVEL
import cml.utils.ModelConfig.Resource.INIT_VALUE
import org.scalatest.FunSuite

/**
  * @author Monica Gondolini
  */
class ResourceProductionTest extends FunSuite{

  val x1 = 10
  val y1 = 10
  val x2 = 5
  val y2 = 5
  val x3 = 100
  val y3 = 100

  test("Resource production during time test") {
    val farm: Farm = Farm(Position(x1, y1), B_INIT_LEVEL)
    val cave: Cave = Cave(Position(x2, y2), B_INIT_LEVEL)
    val habitat = Habitat(AIR,Position(x3, y3), H_INIT_LEVEL)

    val timer = new Timer()
    val task = new TimerTask {
      def run(): Unit = {
        farm.food.inc(farm.building_level)
        cave.money.inc(cave.building_level)
        habitat.money.inc(habitat.level)
//        println("farm food "+  farm.food.foodAmount +
//          " cave money " + cave.money.amount
//          +" habitat money " + habitat.money.amount)
      }
    }
    val delay = 0
    val period = 1000L
    val millis = 3000
    timer.schedule(task, delay, period)
    Thread.sleep(millis)

    assert(farm.food.amount > INIT_VALUE && cave.money.amount > INIT_VALUE && habitat.money.amount > INIT_VALUE)
  }

  test("Resource retrieve test"){
    val farm: Farm = Farm(Position(x1, y1), B_INIT_LEVEL)
    val cave: Cave = Cave(Position(x2, y2), B_INIT_LEVEL)
    val habitat = Habitat(AIR,Position(x3, y3), B_INIT_LEVEL)

    for(i <- 1 to 10){
      farm.food.inc(farm.building_level)
      cave.money.inc(cave.building_level)
      habitat.money.inc(habitat.level)
    }

    println("food: " + farm.food.amount + " money: " + habitat.money.amount)

    val foodTaken = farm.food.take()
    println("food taken from farm: " + foodTaken + " food amount now: " + farm.food.amount)

    val caveMoneyTaken = cave.money.take()
    println("money taken from cave : "+ caveMoneyTaken + " money amount now: " + habitat.money.amount)

    val habitatMoneyTaken = habitat.money.take()
    println("money taken from habitat: "+ habitatMoneyTaken + " money amount now: " + habitat.money.amount)

    assert(farm.food.amount.equals(INIT_VALUE) && cave.money.amount.equals(INIT_VALUE) && habitat.money.amount.equals(INIT_VALUE))
  }

}
