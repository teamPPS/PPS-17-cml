package cml.model.creatures

import cml.model.base.Creature
import cml.utils.ModelConfig.Elements.WATER
import cml.utils.ModelConfig.Creature.WATERDEMON
import play.api.libs.json.Json

/**
  * This class models a Water Demon
  * @param creatureName name of the Water Demon
  * @param creatureLevel set the Water Demon level
  * @author Filippo Portolani, (edited by) ecavina
  */

case class WaterDemon(creature_name: String, creature_level: Int) extends Creature {

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

  override def element: String = _element

  override def level: Int = currentLevel

  override def attackPower: Int = attackValue

  override def currentLevel_ : Unit = {
    currentLevel = creature_level
    setAttack()
  }


  override def name: String = creature_name

  override def creatureType: String = WATERDEMON
}

object WaterDemon {
  implicit val reader = Json.format[WaterDemon]
}

