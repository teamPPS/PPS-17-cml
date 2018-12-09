package cml.model.creatures

import cml.model.base.Creature
import cml.utils.ModelConfig.Elements.AIR
import cml.utils.ModelConfig.Creature.GRIFFIN
import play.api.libs.json.Json

/**
  * This class models a griffin
  * @param creatureName name of the griffin
  * @param creatureLevel set the griffin level
  * @author Filippo Portolani
  */

case class Griffin(creature_name: String, creature_level: Int) extends Creature {

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
    currentLevel = creature_level
    setAttack()
  }

  override def name: String = creature_name

  override def creatureType: String = GRIFFIN
}

object Griffin {
  implicit val reader = Json.format[Griffin]
}

