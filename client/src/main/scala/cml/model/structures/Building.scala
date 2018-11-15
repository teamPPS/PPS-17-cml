package cml.model.structures

import java.util.{Timer, TimerTask}

import cml.utils.ModelConfig.Building.{LEVEL_INIT, TYPE_FARM}

/**
  * Class to generate a building in the village
  * @author Monica Gondolini
  */
case class Building(buildingId: Int,
                    buildingType: String = TYPE_FARM,
                    buildingPosition: Position,
                    buildingLevel: Int = LEVEL_INIT) {

  println(buildingType, buildingLevel)
}

object Building{

  /**
    * Produces resources with the passing of time
    * @return resource generated
    */
  def produce(): Int = {
    var food: Int = 0
    println(food)
    val timer = new Timer()
    val task = new TimerTask{
      def run(): Unit = {
        food += 1
        println("asd"+food)
      }
    }
    timer.schedule(task, 0L, 10L)
    food
  }

}
