package cml.model

import java.util.{Timer, TimerTask}

import cml.model.structures._
import org.scalatest.AsyncFunSuite
import cml.utils.ModelConfig.Building.{LEVEL_INIT, TYPE_FARM}

class BuildingTest extends AsyncFunSuite{

  test("Food production test"){
    val building: Building = Building(TYPE_FARM, Position(10,10), LEVEL_INIT)
    building.levelUp()
    val food = Food(LEVEL_INIT)
    println(building.buildingLevel)

      val timer = new Timer()
      val task = new TimerTask{
        def run(): Unit = {
          food.inc()
          println("food "+ food.value)
        }
      }
      timer.schedule(task, 0, 1000L)


    Thread.sleep(10000)
    assert(1==1)
  }

}
