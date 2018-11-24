package cml.model.creatures

import cml.model.base.Creature

/**
  *This class models a dragon
  * @param creatureName name of the dragon
  * @param creatureElement element which characterises the dragon
  * @param creatureLevel level of the dragon
  * @author Filippo Portolani
  */

case class Dragon(creatureName: String, creatureElement: String) extends Creature{

  /**
    * This method increases creature level by 1
    */

  override def levelUp(): Unit = currentLevel += 1

  /**
    * This method behaves differently based on the creature level
    */

  override def attack() : Unit = {
    currentLevel match {
      case 10 => attackValue += 10
      case 20 => attackValue +=10
      case 30 => attackValue +=10  //we can add more levels
    }
  }
}
