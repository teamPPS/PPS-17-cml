package cml.model.creatures

import cml.model.base.Creature
import cml.utils.ModelConfig.Elements.FIRE
import play.api.libs.json.Json

/**
  * This class models a dragon
  * @param creatureName name of the dragon
  * @param creatureLevel set the dragon level
  * @author Filippo Portolani
  */

case class Dragon(creature_name: String, creature_level: Int) extends Creature{

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
    currentLevel = creature_level
    setAttack()
  }
}

object Dragon {
  implicit val dragonReader = Json.format[Dragon]
}
