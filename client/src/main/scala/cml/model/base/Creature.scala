package cml.model.base

import cml.utils.ModelConfig

/**
  * @author Filippo Portolani
  */

trait Creature {

  def levelUp(): Unit
}

/**
  * This class models a creature
  * @param creatureName name of the creature
  * @param creatureElement the type of the creature
  * @param creatureLevel level of the creature
  * @param habitatPosition where the creature must be positioned
  */

case class Creature1(creatureName: String, creatureElement: String,var creatureLevel: Int, habitatPosition: Position ) extends Creature { //could be a dragon or something else

  val initialHp = ModelConfig.Creature.HEALTH_POINT
  override def levelUp(): Unit = creatureLevel += 1
}




