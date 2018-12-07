package cml.model.creatures

import cml.model.base.Creature
import cml.utils.ModelConfig.Elements.EARTH
import cml.utils.ModelConfig.Creature.GOLEM

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
    setAttack()
  }

  /**
    * Golem earns 15 more damage instead of 10
    */

  override def setAttack(): Unit = {
    currentLevel match {
      case 10 => attackValue += 15
      case 20 => attackValue += 15
      case 30 => attackValue += 15
      case _ => ;
    }
  }

  override def element: String = _element

  override def level: Int = currentLevel

  override def attackPower: Int = attackValue

  override def currentLevel_ : Unit = {
    currentLevel = creatureLevel
    setAttack()
  }

  override def name: String = creatureName

  override def creatureType: String = GOLEM
}
