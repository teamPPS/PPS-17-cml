package cml.model.dynamic_model

import cml.model.base.Creature
import cml.utils.CreatureJson
import play.api.libs.json.JsValue

trait UpgradeCreature {
  val creatureJson: JsValue
}

case class CreatureUpgrade(creature: Creature) extends UpgradeCreature {
  creature.levelUp()
  val json = CreatureJson("Smaug", creature.currentLevel).json
  override val creatureJson: JsValue = json
}
