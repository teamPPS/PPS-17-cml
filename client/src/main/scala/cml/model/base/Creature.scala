package cml.model.base

import cml.utils.ModelConfig.Creature._

/**
  * This trait defines a base creature
  * @author Filippo Portolani
  */

trait Creature {

  var currentLevel: Int = INITIAL_LEVEL
  val initialHp: Int = HEALTH_POINT
  var attackValue: Int = ATTACK_VALUE

  def levelUp(): Unit
  def setAttack(): Unit
  def element : String
  def level : Int
  def attackPower : Int
  def currentLevel_ : Unit
  def name: String
  def creatureType: String
}

object Creature{
  var selectedCreature: Option[Creature] = None
  def setSelectedCreature(creature: Option[Creature]): Unit = selectedCreature = creature
}




