package cml.model.creatures

import cml.model.base.Creature
import cml.utils.ModelConfig.Elements.FIRE
import cml.utils.ModelConfig.Creature.DRAGON
import play.api.libs.json.Json

/**
  * This class models a dragon
  * @param creature_name name of the dragon
  * @param creature_level set the dragon level
  *
  * @author Filippo Portolani, (edited by) ecavina
  */

case class FireCreature(creature_name: String, creature_level: Int) extends Creature{

  val _element : String = FIRE

  override def levelUp(): Unit = {
    currentLevel += 1
    setAttack()
  }

  override def setAttack() : Unit = {
    currentLevel match {
      case 10 => attackValue += 10
      case 20 => attackValue += 10
      case 30 => attackValue += 10
      case _ => ;
    }

  }

  override def element: String = _element

  override def level: Int = {
    if(currentLevel < creature_level)
      currentLevel = creature_level
    currentLevel
  }
  override def attackPower: Int = attackValue

  override def currentLevel_ : Unit = {
    currentLevel = creature_level
    setAttack()
  }

  override def name: String = creature_name

  override def creatureType: String = DRAGON
}

object FireCreature {
  implicit val reader = Json.format[FireCreature]
}
