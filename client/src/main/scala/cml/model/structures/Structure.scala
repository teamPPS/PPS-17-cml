package cml.model.structures

/**
  * This trait defines common operations over structures
  * @author Monica Gondolini
  */
trait Structure{
  def levelUp(): Unit
}

/**
  * Implementation of structure building
  * @param buildingType type of the building
  * @param buildingPosition coordinates of the building in the village
  * @param buildingLevel level of the building
  */
case class Building(buildingType: String,
                    buildingPosition: Position,
                    var buildingLevel: Int) extends Structure {

  override def levelUp(): Unit = buildingLevel += 10
}

/**
  * Implementation of structure habitat
  * @param element of the habitat
  * @param habitatPosition coordinates of the habitat in the village
  * @param habitatLevel level of the habitat
  * @param creatures list of creatures living in this habitat
  */
case class Habitat(element: String,
                   habitatPosition: Position,
                   var habitatLevel: Int,
                   creatures: List[String]) extends Structure {//Al posto di String ci andrà tipo Creature

  override def levelUp(): Unit = habitatLevel += 10
}




/*
  //può tornare utile per il controller

  override def produce(): Unit = {
    val timer = new Timer()
    val task = new TimerTask{
      def run(): Unit = {
        food += 10
        println("food "+food)
      }
    }
    timer.schedule(task, 0, 1000L)
  }
*/