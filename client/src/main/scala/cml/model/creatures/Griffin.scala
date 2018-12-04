package cml.model.creatures

import cml.model.base.Creature
import cml.utils.ModelConfig.Elements.AIR

/**
  * This class models a griffin
  * @param creatureName name of the griffin
  * @param creatureLevel set the griffin level
  * @author Filippo Portolani
  */

case class Griffin(creatureName: String, creatureLevel: Int) extends Creature {

  val _element : String = AIR

  override def levelUp(): Unit = {
    currentLevel += 1
    setAttack()
  }

  override def setAttack(): Unit = {
    currentLevel match {
      case 10 => attackValue += 10
      case 20 => attackValue += 10
      case 30 => attackValue += 10
      case _ => ;
    }
  }

  override def level: Int = currentLevel

  override def element: String = _element

  override def attackPower: Int = attackValue

  override def currentLevel_ : Unit = {
    currentLevel = creatureLevel
    setAttack()
  }
}
