package cml.model.creatures

import cml.model.base.Creature
import cml.utils.ModelConfig.Elements.FIRE
import cml.utils.ModelConfig.Creature.DRAGON
import play.api.libs.json.Json

/**
  * This class models a dragon
  * @param creatureName name of the dragon
  * @param creatureLevel set the dragon level
  * @author Filippo Portolani, (edited by) ecavina
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
    * This is a getter of the dragon level
    * @return the current level
    */

  override def level: Int = {
    if(currentLevel < creature_level)
      currentLevel = creature_level
    currentLevel
  }

  /**
    * This is a getter of the dragon attack power
    * @return the current attach value
    */

  override def attackPower: Int = attackValue

  /**
    * This method allows to set the dragon level
    */

  override def currentLevel_ : Unit = {
    currentLevel = creature_level
    setAttack()
  }

  override def name: String = creature_name

  override def creatureType: String = DRAGON
}

object Dragon {
  implicit val reader = Json.format[Dragon]
}
