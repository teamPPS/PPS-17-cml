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
        building.resource.inc(B_INIT_LEVEL)
        habitat.money.inc(B_INIT_LEVEL)
        println(building.resource.resourceType+" "+  building.resource.amount +" money " + habitat.money.moneyAmount)
      }
    }
    timer.schedule(task, 0, 1000L)
    Thread.sleep(10000)

    assert(building.resource.amount > INIT_VALUE && habitat.money.moneyAmount > INIT_VALUE)
  }

  test("Resource retrieve test"){
//    val creatures: List[Creature] = List(Dragon("drago", 1))
    val building: Building = Building(TYPE_FARM, Position(10,10), B_INIT_LEVEL)
    val habitat = Habitat(AIR,Position(100,100), B_INIT_LEVEL)

    for(i <- 1 to 10 ){
      building.resource.inc(building.buildingLevel)
      habitat.money.inc(habitat.habitatLevel)
    }

    println("food: "+building.resource.amount+" money: "+habitat.money.moneyAmount)

    val resTaken = building.resource.take()
    println(building.resource.resourceType +"taken: "+resTaken+ " food amount now: " +building.resource.amount)

    val moneyTaken = habitat.money.take()
    println("money taken: "+moneyTaken+ " money amount now: " +habitat.money.moneyAmount)

    assert(building.resource.amount.equals(INIT_VALUE) && habitat.money.moneyAmount.equals(INIT_VALUE))
  }



}
