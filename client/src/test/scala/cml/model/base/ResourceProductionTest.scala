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

  test("Resource production during time test") {
    val farm: Farm = Farm(Position(10,10), B_INIT_LEVEL)
    val cave: Cave = Cave(Position(5,5), B_INIT_LEVEL)
    val habitat = Habitat(AIR,Position(100,100), H_INIT_LEVEL)

    val timer = new Timer()
    val task = new TimerTask {
      def run(): Unit = {
        farm.food.inc(farm.farmLevel)
        cave.money.inc(cave.caveLevel)
        habitat.money.inc(habitat.level)
//        println("farm food "+  farm.food.foodAmount +
//          " cave money " + cave.money.amount
//          +" habitat money " + habitat.money.amount)
      }
    }
    timer.schedule(task, 0, 1000L)
    Thread.sleep(10000)

    assert(farm.food.amount > INIT_VALUE && cave.money.amount > INIT_VALUE && habitat.money.amount > INIT_VALUE)
  }

  test("Resource retrieve test"){
    val farm: Farm = Farm(Position(10,10), B_INIT_LEVEL)
    val cave: Cave = Cave(Position(5,5), B_INIT_LEVEL)
    val habitat = Habitat(AIR,Position(100,100), B_INIT_LEVEL)

    for(i <- 1 to 10){
      farm.food.inc(farm.farmLevel)
      cave.money.inc(cave.caveLevel)
      habitat.money.inc(habitat.level)
    }

    println("food: "+farm.food.amount+" money: "+habitat.money.amount)

    val foodTaken = farm.food.take()
    println("food taken from farm: "+foodTaken+ " food amount now: " +farm.food.amount)

    val caveMoneyTaken = cave.money.take()
    println("money taken from cave : "+caveMoneyTaken+ " money amount now: " +habitat.money.amount)

    val habitatMoneyTaken = habitat.money.take()
    println("money taken from habitat: "+habitatMoneyTaken+ " money amount now: " +habitat.money.amount)

    assert(farm.food.amount.equals(INIT_VALUE) && cave.money.amount.equals(INIT_VALUE) && habitat.money.amount.equals(INIT_VALUE))
  }

}
