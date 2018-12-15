package cml.model.base

import cml.utils.ModelConfig.Resource._
import play.api.libs.json.Json
import scala.collection.mutable

/**
  * This trait defines common operations over structures
  * @author Monica Gondolini, ecavina
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

  def addCreature(creature: Creature): Unit
  def creatures: mutable.MutableList[Creature]
  def habitatElement: String
}

/**
  * Implementation of building structure Farm
  * @param building_pos coordinates of the building in the village
  * @param building_level level of the building
  */
case class Farm(building_pos: Position, var building_level: Int) extends Structure { //TODO refactoring nome parametri (adesso devono corrispondere al campo json)
  val food = Food(INIT_VALUE)
  override def levelUp(): Unit = building_level += 1
  override def level: Int = building_level
  override def position: Position = building_pos
  override def resource: Resource = food

  override def addCreature(creature: Creature): Unit = {
    if(creature != null)
      throw new NoSuchElementException
  }
  override def creatures: mutable.MutableList[Creature] = null
  override def habitatElement: String = "Not an habitat"
}

object Farm {
  implicit val farmReader = Json.format[Farm]
}

/**
  * Implementation of building structure Cave
  * @param building_pos coordinates of the building in the village
  * @param building_level level of the building
  */
case class Cave(building_pos: Position, var building_level: Int) extends Structure { //TODO refactoring nome parametri (adesso devono corrispondere al campo json)
  val money = Money(INIT_VALUE)
  override def levelUp(): Unit = building_level += 1
  override def level: Int = building_level
  override def position: Position = building_pos
  override def resource: Resource = money

  override def addCreature(creature: Creature): Unit = {
    if(creature != null)
      throw new NoSuchElementException
  }
  override def creatures: mutable.MutableList[Creature] = null
  override def habitatElement: String = "Not an habitat"
}

object Cave {
  implicit val caveReader = Json.format[Cave]
}

object Habitat {

  def apply(habitatElement: String, habitatPosition: Position, habitatLevel: Int) : Habitat =
     Habitat(habitatElement, habitatPosition, habitatLevel)

  /**
    * Implementation of structure habitat
    * @param element         of the habitat
    * @param habitat_pos coordinates of the habitat in the village
    * @param habitat_level    level of the habitat
    */
  case class Habitat(element: String, habitat_pos: Position, var habitat_level: Int) extends Structure {
    var creatureList: mutable.MutableList[Creature] = mutable.MutableList[Creature]()
    val money = Money(INIT_VALUE) //crea più denaro in base al numero di creature  e al livello delle creature(?)
    override def levelUp(): Unit = habitat_level += 1
    override def level: Int = habitat_level
    override def position: Position = habitat_pos
    override def resource: Resource = money
    override def addCreature(creature: Creature): Unit = creatureList += creature
    override def creatures: mutable.MutableList[Creature] = creatureList
    override def habitatElement: String = element
  }

  implicit val habitatReader = Json.format[Habitat]

}

