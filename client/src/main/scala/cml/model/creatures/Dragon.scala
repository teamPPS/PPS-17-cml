package cml.model.creatures

import cml.model.base.Creature1
import cml.utils.ModelConfig.Creature.INITIAL_LEVEL
import cml.utils.ModelConfig.Elements.AIR

/**
  * @author Filippo Portolani
  */

/**
  * This class models a dragon named Smaug
  */

class Dragon extends Creature1("Smaug", AIR, INITIAL_LEVEL){

  /**
    * This method behaves differently based on the creature level
    */

  override def attack() : Unit = {
    creatureLevel match {
      case _ >= 10 => attackValue += 10
      case _ >=20 => attackValue +=10
      case _ >=30 => attackValue +=10  //we can add more levels
    }
  }

}
