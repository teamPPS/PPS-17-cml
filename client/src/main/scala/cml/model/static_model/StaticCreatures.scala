package cml.model.static_model

import cml.model.base.{Creature, Structure}
import cml.model.creatures.{Dragon, Golem, Griffin, Kraken}
import cml.utils.CreatureJson
import cml.utils.ModelConfig.Creature._
import cml.utils.ModelConfig.Elements.{AIR, EARTH, FIRE, WATER}
import play.api.libs.json.JsValue

/**
  * @author Monica Gondolini
  */

trait StaticCreature {
  val json: JsValue
  val getCreature: Creature
}

case class StaticCreatures(s: Structure) extends StaticCreature {

  private var creature: Creature = _
  private var creatureJson: JsValue = _

  s.element match {
    case FIRE =>
      creature = Dragon(DRAGON_NAME,INITIAL_LEVEL)
      creatureJson = CreatureJson(DRAGON_NAME,INITIAL_LEVEL).json //passare anche il tipo drago/kraken ecc ?
    case WATER =>
      creature = Kraken(KRAKEN_NAME, INITIAL_LEVEL)
      creatureJson = CreatureJson(KRAKEN_NAME,INITIAL_LEVEL).json
    case EARTH =>
      creature = Golem(GOLEM_NAME, INITIAL_LEVEL)
      creatureJson = CreatureJson(GOLEM_NAME,INITIAL_LEVEL).json
    case AIR =>
      creature = Griffin(GRIFFIN_NAME, INITIAL_LEVEL)
      creatureJson = CreatureJson(GRIFFIN_NAME,INITIAL_LEVEL).json
    case "Not an habitat" => throw new IllegalArgumentException
  }

  override val json: JsValue = creatureJson
  override val getCreature: Creature = creature
}
