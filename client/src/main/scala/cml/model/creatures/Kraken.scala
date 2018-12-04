package cml.model.creatures

import cml.model.base.Creature
import cml.utils.ModelConfig.Elements.WATER

/**
  * This class models a kraken
  * @param creatureName name of the kraken
  * @param creatureLevel set the kraken level
  * @author Filippo Portolani
  */

case class Kraken(creatureName: String, creatureLevel: Int) extends Creature {

  val _element : String = WATER

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
    * This method allows to set the dragon level(we retrieve the creature from the server)
    */

  override def element: String = _element

  override def currentLevel_ : Unit = {
    currentLevel = creatureLevel
    setAttack()
  }


  override def name: String = creatureName

  override def creatureType: String = "Kraken"
}
