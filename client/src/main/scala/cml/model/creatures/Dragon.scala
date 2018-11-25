package cml.model.creatures

import cml.model.base.Creature

/**
  *This class models a dragon
  * @param creatureName name of the dragon
  * @param creatureElement element which characterises the dragon
  * @param creatureLevel set the dragon level
  * @author Filippo Portolani
  */

case class Dragon(creatureName: String, creatureElement: String, creatureLevel: Int) extends Creature{

  /**
    * This method increases creature level by 1
    */

  override def levelUp(): Unit = currentLevel += 1

  /**
    * This method behaves differently based on the creature level
    */

  override def setAttack() : Unit = {
    currentLevel match {
      case 10 => attackValue += 10
      case 20 => attackValue +=10
      case 30 => attackValue +=10  //we can add more levels
    }
  }

  /**
    * This method allow to set the dragon level(we retrieve the creature from the server)
    */

  override def setLevel(): Unit = {
    currentLevel = creatureLevel
  }
}
