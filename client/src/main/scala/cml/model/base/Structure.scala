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

  def apply(hElement: String, hPosition: Position, hLevel: Int): SingleHabitat =
    SingleHabitat(hElement, hPosition, hLevel)

  /**
    * Implementation of structure habitat
    * @param element         of the habitat
    * @param position coordinates of the habitat in the village
    * @param level    level of the habitat
    */
  case class Habitat(element: String, position: Position, var level: Int) extends Structure {
    private var creatures: mutable.MutableList[Creature] = mutable.MutableList[Creature]()
    val money = Money(INIT_VALUE) //crea più denaro in base al numero di creature  e al livello delle creature(?)
    override def levelUp(): Unit = level += 1
    override def getLevel: Int = level
    override def getPosition: Position = position
    override def resource: Resource = money
    def addCreature(creature: Creature): Unit = creatures += creature
  }

  /**
    * Implementation of structure habitat
    * @param hElement         of the habitat
    * @param hPosition coordinates of the habitat in the village
    * @param hLevel    level of the habitat
    */
  case class SingleHabitat(hElement: String, hPosition: Position, var hLevel: Int) extends Structure {
    val money = Money(INIT_VALUE)
    private var creature: Creature = _
    override def levelUp(): Unit = hLevel += 1
    override def getLevel: Int = hLevel
    override def getPosition: Position = hPosition
    override def resource: Resource = money
    def addCreature(c: Creature): Unit = creature = c
  }

}

