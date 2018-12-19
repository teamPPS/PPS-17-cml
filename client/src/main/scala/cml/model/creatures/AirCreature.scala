package cml.model.creatures

import cml.model.base.Creature
import cml.utils.ModelConfig.Elements.AIR
import cml.utils.ModelConfig.Creature.GRIFFIN
import play.api.libs.json.Json

/**
  * This class models a griffin
  * @param creature_name name of the griffin
  * @param creature_level set the griffin level
  * @author Filippo Portolani
  */

case class AirCreature(creature_name: String, creature_level: Int) extends Creature {

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

  override def level: Int = {
    if(currentLevel < creature_level)
        currentLevel = creature_level
    currentLevel
  }

  override def element: String = _element

  override def attackPower: Int = attackValue

  override def currentLevel_ : Unit = {
    currentLevel = creature_level
    setAttack()
  }

  override def name: String = creature_name

  override def creatureType: String = GRIFFIN
}

object AirCreature {
  implicit val reader = Json.format[AirCreature]
}

