package cml.model.creatures

import cml.model.base.Creature
import cml.utils.ModelConfig.Elements.EARTH

/**
  * This class models a golem
  * @param creatureName name of the golem
  * @param creatureLevel set the golem level
  * @author Filippo Portolani
  */

case class Golem(creatureName: String, creatureLevel: Int) extends Creature {

  val _element : String = EARTH

  override def levelUp(): Unit = {
    currentLevel += 1
  }

  /**
    * Golem earns 15 more damage instead of 10
    */

  override def setAttack(): Unit = {
    currentLevel match {
      case 10 => attackValue += 15
      case 20 => attackValue += 15
      case 30 => attackValue += 15
    }
  }

  override def element: String = _element

  override def currentLevel_ : Unit = {
    currentLevel = creatureLevel
  }
}