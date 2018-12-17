package cml.model.static_model

import cml.model.base.{Creature, Structure}
import cml.model.creatures.{FireCreature, EarthCreature, AirCreature, WaterCreature}
import cml.utils.CreatureJson
import cml.utils.ModelConfig.Creature._
import cml.utils.ModelConfig.Elements.{AIR, EARTH, FIRE, WATER}
import play.api.libs.json.JsValue

/**
  * @author Monica Gondolini
  */

trait StaticCreature {
  def json: JsValue
}

case class StaticCreatures(s: Structure) extends StaticCreature {

  private var creature: Creature = _
  private var creatureJson: JsValue = _

  if(s.habitatElement.nonEmpty)
    s.habitatElement.get match {
      case FIRE =>
        creature = FireCreature(DRAGON_NAME,INITIAL_LEVEL)
        creatureJson = CreatureJson(DRAGON_NAME,INITIAL_LEVEL, DRAGON, s).json //passare anche il tipo drago/kraken ecc ?
      case WATER =>
        creature = WaterCreature(WATERDEMON_NAME, INITIAL_LEVEL)
        creatureJson = CreatureJson(WATERDEMON_NAME,INITIAL_LEVEL, WATERDEMON, s).json
      case EARTH =>
        creature = EarthCreature(GOLEM_NAME, INITIAL_LEVEL)
        creatureJson = CreatureJson(GOLEM_NAME,INITIAL_LEVEL, GOLEM, s).json
      case AIR =>
        creature = AirCreature(GRIFFIN_NAME, INITIAL_LEVEL)
        creatureJson = CreatureJson(GRIFFIN_NAME,INITIAL_LEVEL, GRIFFIN, s).json
      case "Not an habitat" => throw new IllegalArgumentException
    }

  s.addCreature(creature)

  override def json: JsValue = creatureJson
}
