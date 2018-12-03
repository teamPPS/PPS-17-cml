package cml.model.dynamic_model

import cml.model.base.Creature
import cml.utils.CreatureJson
import cml.utils.ModelConfig.Creature.INITIAL_LEVEL
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
case class CreatureUpgrade(creature: Creature, level:Int) extends UpgradeCreature {
  for(_ <- INITIAL_LEVEL until level) {
    creature levelUp()
    println(creature.currentLevel)
  }
  private val json: JsValue = CreatureJson(creature name, creature.currentLevel) json
  override def creatureJson: JsValue = json
}
