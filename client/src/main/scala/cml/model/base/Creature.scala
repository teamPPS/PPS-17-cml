package cml.model.base

import cml.utils.ModelConfig.Creature._

/**
  * This trait defines a base creature
  * @author Filippo Portolani
  */

trait   Creature {

  var currentLevel: Int = INITIAL_LEVEL
  val initialHp: Int = HEALTH_POINT
  var attackValue: Int = ATTACK_VALUE

  /**
    * This method increases creature level by 1
    */
  def levelUp(): Unit

  /**
    * This method behaves differently based on the creature level
    */
  def setAttack(): Unit

  /**
    * This is a getter of the dragon element
    * @return the element
    */
  def element : String

  /**
    * This is a getter of the dragon level
    * @return the current level
    */
  def level : Int

  /**
    * This is a getter of the dragon attack power
    * @return the current attach value
    */
  def attackPower : Int

  /**
    * This method allows to set the dragon level
    */
  def currentLevel_ : Unit

  /**
    * This method get creature name
    * @return creature name
    */
  def name: String

  /**
    * This method get creature element
    * @return creature element
    */
  def creatureType: String
}

object Creature{
  var selectedCreature: Option[Creature] = None
  def setSelectedCreature(creature: Option[Creature]): Unit = selectedCreature = creature
}




