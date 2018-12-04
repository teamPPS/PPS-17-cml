package cml.model.base

import cml.utils.ModelConfig.Creature._

/**
  * This trait defines a base creature
  * @author Filippo Portolani
  */

trait Creature {

  var currentLevel = INITIAL_LEVEL
  val initialHp = HEALTH_POINT
  var attackValue = ATTACK_VALUE

  def levelUp(): Unit
  def setAttack(): Unit
  def element: String
  def currentLevel_ : Unit
  def name: String
  def creatureType: String
}




