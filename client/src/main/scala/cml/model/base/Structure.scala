package cml.model.base

import cml.model.static_model.{StaticBuilding, StaticHabitat}
import cml.utils.ModelConfig.Resource._
import cml.utils.ModelConfig.Building._

/**
  * This trait defines common operations over structures
  * @author Monica Gondolini
  */
trait Structure{
  /**
    * Increments Structure level
    */
  def levelUp(): Unit

  /**
    * Get coordinates
    */
  def getPosition: Position
}

/**
  * Implementation of structure building
  * @param buildingType type of the building
  * @param buildingPosition coordinates of the building in the village
  * @param buildingLevel level of the building
  */
case class Building(buildingType: String, buildingPosition: Position, var buildingLevel: Int) extends StaticBuilding(buildingType, buildingLevel) with Structure {

  val resource: Resource = Building.resource(buildingType)

//  val food = Food(INIT_VALUE)
  override def levelUp(): Unit = buildingLevel += 1
  override def getPosition: Position = buildingPosition
}

object Building {
  private def resource(buildingType:String): Resource = buildingType match {
    case TYPE_FARM => Food(INIT_VALUE)
    case TYPE_CAVE => Money(INIT_VALUE)
    case _ => throw new NoSuchElementException
  }
}


object Habitat {

  def apply(element: String, habitatPosition: Position, habitatLevel: Int) : Habitat =
     Habitat(element, habitatPosition, habitatLevel)

  def apply(element: String, habitatPosition: Position, habitatLevel: Int, creature: Creature): SingleHabitat =
    SingleHabitat(element, habitatPosition, habitatLevel, creature)

  /**
    * Implementation of structure habitat
    * @param element         of the habitat
    * @param habitatPosition coordinates of the habitat in the village
    * @param habitatLevel    level of the habitat
//    * @param creatures       list of creatures living in this habitat
    */
  case class Habitat(element: String, habitatPosition: Position, var habitatLevel: Int) extends StaticHabitat(element, habitatLevel) with Structure {
    val creatures: List[Creature] = List[Creature]()
    val money = Money(INIT_VALUE) //crea più denaro in base al numero di creature  e al livello delle creature(?)
    override def levelUp(): Unit = habitatLevel += 1
    override def getPosition: Position = habitatPosition
  }

  /**
    * Implementation of structure habitat
    * @param element         of the habitat
    * @param habitatPosition coordinates of the habitat in the village
    * @param habitatLevel    level of the habitat
    * @param creature        single creature living in this habitat
    */
  case class SingleHabitat(element: String, habitatPosition: Position, var habitatLevel: Int, creature: Creature) extends StaticHabitat(element, habitatLevel) with Structure {
    val money = Money(INIT_VALUE)
    override def levelUp(): Unit = habitatLevel += 1
    override def getPosition: Position = habitatPosition
  }

}

