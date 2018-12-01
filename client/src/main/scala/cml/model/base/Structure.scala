package cml.model.base

import cml.utils.ModelConfig.Resource._

import scala.collection.mutable

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
    * Get structure level
    */
  def getLevel: Int
  /**
    * Get structure coordinates
    */
  def getPosition: Position

  /**
    * Get resource
    * @return resource type
    */
  def resource: Resource
}

/**
  * Implementation of building structure Farm
  * @param position coordinates of the building in the village
  * @param level level of the building
  */
case class Farm(position: Position, var level: Int) extends Structure {
  val food = Food(INIT_VALUE)
  override def levelUp(): Unit = level += 1
  override def getLevel: Int = level
  override def getPosition: Position = position
  override def resource: Resource = food
}

/**
  * Implementation of building structure Cave
  * @param position coordinates of the building in the village
  * @param level level of the building
  */
case class Cave(position: Position, var level: Int) extends Structure {
  val money = Money(INIT_VALUE)
  override def levelUp(): Unit = level += 1
  override def getLevel: Int = level
  override def getPosition: Position = position
  override def resource: Resource = money
}

object Habitat {

  def apply(element: String, position: Position, level: Int) : Habitat =
     Habitat(element, position, level)

  def apply(element: String, position: Position, level: Int, creature: Creature): SingleHabitat =
    SingleHabitat(element, position, level, creature)

  /**
    * Implementation of structure habitat
    * @param element         of the habitat
    * @param position coordinates of the habitat in the village
    * @param level    level of the habitat
//    * @param creatures       list of creatures living in this habitat
    */
  case class Habitat(element: String, position: Position, var level: Int) extends Structure {
    val creatures: mutable.MutableList[Creature] = mutable.MutableList[Creature]()
    val money = Money(INIT_VALUE) //crea più denaro in base al numero di creature  e al livello delle creature(?)
    override def levelUp(): Unit = level += 1
    override def getLevel: Int = level
    override def getPosition: Position = position
    override def resource: Resource = money
  }

  /**
    * Implementation of structure habitat
    * @param element         of the habitat
    * @param position coordinates of the habitat in the village
    * @param level    level of the habitat
    * @param creature        single creature living in this habitat
    */
  case class SingleHabitat(element: String, position: Position, var level: Int, creature: Creature) extends Structure {
    val money = Money(INIT_VALUE)
    override def levelUp(): Unit = level += 1
    override def getLevel: Int = level
    override def getPosition: Position = position
    override def resource: Resource = money
  }

}

