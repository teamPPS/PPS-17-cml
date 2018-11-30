package cml.model.base

import java.util.{Timer, TimerTask}
import cml.model.creatures.Dragon
import cml.utils.ModelConfig.Building.{B_INIT_LEVEL, TYPE_FARM}
import cml.utils.ModelConfig.Elements.AIR
import cml.utils.ModelConfig.Resource.INIT_VALUE
import cml.utils.ModelConfig.Creature.INITIAL_LEVEL
import org.scalatest.FunSuite

/**
  * @author Monica Gondolini
  */
class ResourceProductionTest extends FunSuite{

  test("Resource production during time test") {
//    val creatures: List[Creature] = List(Dragon("drago", 1))
    val building: Building = Building(TYPE_FARM, Position(10,10), B_INIT_LEVEL)
    val habitat = Habitat(AIR,Position(100,100), B_INIT_LEVEL)

    val timer = new Timer()
    val task = new TimerTask {
      def run(): Unit = {
        building.food.inc(B_INIT_LEVEL)
        habitat.money.inc(B_INIT_LEVEL)
        println("food " +  building.food.amount +" money " + habitat.money.amount)
      }
    }
    timer.schedule(task, 0, 1000L)
    Thread.sleep(10000)

    assert(building.food.amount > INIT_VALUE && habitat.money.amount > INIT_VALUE)
  }

  test("Resource retrieve test"){
//    val creatures: List[Creature] = List(Dragon("drago", 1))
    val building: Building = Building(TYPE_FARM, Position(10,10), B_INIT_LEVEL)
    val habitat = Habitat(AIR,Position(100,100), B_INIT_LEVEL)

    for(i <- 1 to 10 ){
      building.food.inc(building.buildingLevel)
      habitat.money.inc(habitat.habitatLevel)
    }

    println("food: "+building.food.amount+" money: "+habitat.money.amount)

    val foodTaken = building.food.take()
    println("food taken: "+foodTaken+ " food amount now: " +building.food.amount)

    val moneyTaken = habitat.money.take()
    println("money taken: "+moneyTaken+ " money amount now: " +habitat.money.amount)

    assert(building.food.amount.equals(INIT_VALUE) && habitat.money.amount.equals(INIT_VALUE))
  }



}
