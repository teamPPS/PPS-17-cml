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
  def level: Int
  /**
    * Get structure coordinates
    */
  def position: Position

  /**
    * Get resource
    * @return resource type
    */
  def resource: Resource
}

/**
  * Implementation of building structure Farm
  * @param farmPosition coordinates of the building in the village
  * @param farmLevel level of the building
  */
case class Farm(farmPosition: Position, var farmLevel: Int) extends Structure {
  val food = Food(INIT_VALUE)
  override def levelUp(): Unit = farmLevel += 1
  override def level: Int = farmLevel
  override def position: Position = farmPosition
  override def resource: Resource = food
}

/**
  * Implementation of building structure Cave
  * @param cavePosition coordinates of the building in the village
  * @param caveLevel level of the building
  */
case class Cave(cavePosition: Position, var caveLevel: Int) extends Structure {
  val money = Money(INIT_VALUE)
  override def levelUp(): Unit = caveLevel += 1
  override def level: Int = caveLevel
  override def position: Position = cavePosition
  override def resource: Resource = money
}

object Habitat {

  def apply(habitatElement: String, habitatPosition: Position, habitatLevel: Int) : Habitat =
     Habitat(habitatElement, habitatPosition, habitatLevel)

  /**
    * Implementation of structure habitat
    * @param habitatElement         of the habitat
    * @param habitatPosition coordinates of the habitat in the village
    * @param habitatLevel    level of the habitat
    */
  case class Habitat(habitatElement: String, habitatPosition: Position, var habitatLevel: Int) extends Structure {
    private var creatures: mutable.MutableList[Creature] = mutable.MutableList[Creature]()
    val money = Money(INIT_VALUE) //crea più denaro in base al numero di creature  e al livello delle creature(?)
    override def levelUp(): Unit = habitatLevel += 1
    override def level: Int = habitatLevel
    override def position: Position = habitatPosition
    override def resource: Resource = money
    def addCreature(creature: Creature): Unit = creatures += creature
  }

}

