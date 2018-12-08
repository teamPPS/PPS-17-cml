package cml.model.static_model

import cml.model.base.{Creature, Structure}
import cml.model.creatures.{Creatures, Golem, Griffin, WaterDemon}
import cml.utils.CreatureJson
import cml.utils.ModelConfig.Creature._
import cml.utils.ModelConfig.Elements.{AIR, EARTH, FIRE, WATER}
import play.api.libs.json.JsValue

/**
  * @author Monica Gondolini
  */

trait StaticCreature {
  def json: JsValue
  def getCreature: Creature
}

case class StaticCreatures(s: Structure) extends StaticCreature {

  private var creature: Creature = _
  private var creatureJson: JsValue = _

  s.habitatElement match {
    case FIRE =>
      creature = Creatures(DRAGON_NAME,INITIAL_LEVEL)
      creatureJson = CreatureJson(DRAGON_NAME,INITIAL_LEVEL).json //passare anche il tipo drago/kraken ecc ?
    case WATER =>
      creature = WaterDemon(WATERDEMON_NAME, INITIAL_LEVEL)
      creatureJson = CreatureJson(WATERDEMON_NAME,INITIAL_LEVEL).json
    case EARTH =>
      creature = Golem(GOLEM_NAME, INITIAL_LEVEL)
      creatureJson = CreatureJson(GOLEM_NAME,INITIAL_LEVEL).json
    case AIR =>
      creature = Griffin(GRIFFIN_NAME, INITIAL_LEVEL)
      creatureJson = CreatureJson(GRIFFIN_NAME,INITIAL_LEVEL).json
    case "Not an habitat" => throw new IllegalArgumentException
  }

  override def json: JsValue = creatureJson
  override def getCreature: Creature = creature
}
