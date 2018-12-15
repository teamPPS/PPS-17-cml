package cml.model.creatures

import cml.model.base.Creature
import cml.utils.ModelConfig.Elements.EARTH
import cml.utils.ModelConfig.Creature.GOLEM
import play.api.libs.json.Json

/**
  * This class models a golem
  * @param creature_name name of the golem
  * @param creature_level set the golem level
  * @author Filippo Portolani
  */
case class EarthCreature(creature_name: String, creature_level: Int) extends Creature {

  val _element : String = EARTH

  override def levelUp(): Unit = {
    currentLevel += 1
    setAttack()
  }

  /**
    * Golem earns 15 more damage instead of 10
    */

  override def setAttack(): Unit = {
    currentLevel match {
      case 10 => attackValue += 15
      case 20 => attackValue += 15
      case 30 => attackValue += 15
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

  override def creatureType: String = GOLEM
}

object EarthCreature {
  implicit val reader = Json.format[EarthCreature]
}

