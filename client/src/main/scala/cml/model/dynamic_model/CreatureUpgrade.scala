package cml.model.dynamic_model

import cml.model.base.{Creature, Structure}
import cml.utils.CreatureJson
import play.api.libs.json.JsValue

/**
  * @author Monica Gondolini
  */

trait UpgradeCreature {
  def creatureJson: JsValue
}

/**
  * Upgrade a creature level
  * @param creature creature to model
  */
case class CreatureUpgrade(creature: Creature, habitat: Structure) extends UpgradeCreature {

  creature levelUp()
  private val json: JsValue = CreatureJson(creature name, creature.currentLevel, creature.creatureType, habitat) json

  override def creatureJson: JsValue = json
}
