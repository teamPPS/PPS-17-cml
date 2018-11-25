package cml.model.base

import cml.utils.ModelConfig

/**
  * This trait defines a base creature
  * @author Filippo Portolani
  */

trait Creature {

  val initialLevel = ModelConfig.Creature.INITIAL_LEVEL
  var currentLevel = ModelConfig.Creature.INITIAL_LEVEL
  val initialHp = ModelConfig.Creature.HEALTH_POINT
  var attackValue = ModelConfig.Creature.ATTACK_VALUE
  def levelUp(): Unit
  def setAttack(): Unit
  def setLevel(): Unit
}




