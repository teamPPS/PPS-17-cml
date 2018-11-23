package cml.model.base

import java.io.UncheckedIOException

import cml.utils.ModelConfig

/**
  * @author Filippo Portolani
  */

trait Creature {
  /**
    * Increments Creature level
    */
  def levelUp(): Unit
  def attack(): Unit
}

/**
  * This class models a creature
  * @param creatureName name of the creature
  * @param creatureElement the type of the creature
  * @param creatureLevel level of the creature
  */

case class Creature1(creatureName: String, creatureElement: String,var creatureLevel: Int) extends Creature { //could be a dragon or something else

  val initialHp = ModelConfig.Creature.HEALTH_POINT
  var attackValue = ModelConfig.Creature.ATTACK_VALUE
  override def levelUp(): Unit = creatureLevel += 1
  override def attack(): Unit = _
}




