package cml.model.base

import cml.utils.ModelConfig.Resource.INIT_VALUE
import cml.utils.ModelConfig.Building.TYPE_FARM
/**
  * This trait defines common operations over structures
  * @author Monica Gondolini
  */
trait Structure{
  /**
    * Increments Structure level
    */
  def levelUp(): Unit
}

/**
  * Implementation of structure building
  * @param buildingType type of the building
  * @param buildingPosition coordinates of the building in the village
  * @param buildingLevel level of the building
  */
case class Building(buildingType: String, buildingPosition: Position, var buildingLevel: Int) extends Structure {

//  def resource(buildingType: String):Resource = buildingType match{
//    case TYPE_FARM => {
//      Food(INIT_VALUE)
//    }
//  }
  //aggiungere controllo per tipo building
  val food = Food(INIT_VALUE)
  override def levelUp(): Unit = buildingLevel += 1
}


object Habitat {

  def apply(element: String, habitatPosition: Position, habitatLevel: Int, creatures: List[String]) : Habitat =
     Habitat(element, habitatPosition, habitatLevel, creatures)

  def apply(element: String, habitatPosition: Position, habitatLevel: Int, creature: String): SingleHabitat =
    SingleHabitat(element, habitatPosition, habitatLevel, creature)

  /**
    * Implementation of structure habitat
    * @param element         of the habitat
    * @param habitatPosition coordinates of the habitat in the village
    * @param habitatLevel    level of the habitat
    * @param creatures       list of creatures living in this habitat
    */
  case class Habitat(element: String, habitatPosition: Position, var habitatLevel: Int, creatures: List[Creature]) extends Structure { //Al posto di String ci andrà tipo Creature
    val money = Money(INIT_VALUE) //crea più denaro in base al numero di creature  e al livello delle creature(?)
    override def levelUp(): Unit = habitatLevel += 1
  }

  /**
    * Implementation of structure habitat
    * @param element         of the habitat
    * @param habitatPosition coordinates of the habitat in the village
    * @param habitatLevel    level of the habitat
    * @param creature        single creature living in this habitat
    */
  case class SingleHabitat(element: String, habitatPosition: Position, var habitatLevel: Int, creature: Creature) extends Structure {
    val money = Money(INIT_VALUE)
    override def levelUp(): Unit = habitatLevel += 1
  }

}

