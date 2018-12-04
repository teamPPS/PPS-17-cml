package cml.model.creatures

import cml.model.base.Creature
import cml.utils.ModelConfig.Elements.FIRE

/**
  * This class models a dragon
  * @param creatureName name of the dragon
  * @param creatureLevel set the dragon level
  * @author Filippo Portolani
  */

case class Dragon(creatureName: String, creatureLevel: Int) extends Creature{

  val _element : String = FIRE

  /**
    * This method increases creature level by 1
    */

  override def levelUp(): Unit = {
    currentLevel += 1
    setAttack()
  }

  /**
    * This method behaves differently based on the creature level
    */

  override def setAttack() : Unit = {
    currentLevel match {
      case 10 => attackValue += 10
      case 20 => attackValue += 10
      case 30 => attackValue += 10
      case _ => ;
    }
  }

  /**
    * This is a getter of the dragon element
    * @return the element
    */

  override def element: String = _element

  /**
    * This method allows to set the dragon level
    */

  override def currentLevel_ : Unit = {
    currentLevel = creatureLevel
    setAttack()
  }

  override def name: String = creatureName

  override def creatureType: String = "Dragon"
}
