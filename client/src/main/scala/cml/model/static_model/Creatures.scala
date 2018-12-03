package cml.model.static_model

import cml.model.base.{Creature, Habitat, Position, Structure}
import cml.utils.ModelConfig.Elements.{AIR, EARTH, FIRE, WATER}
import cml.utils.ModelConfig.Habitat.H_INIT_LEVEL
import play.api.libs.json.JsValue

/**
  * @author Monica Gondolini
  */

trait NewCreature {
  val json: JsValue
  val creature: Creature
}

case class Creatures(s: Structure) extends NewCreature {

  s.getClass
  case "FIRE_HABITAT" => structure = Habitat(FIRE, Position (x, y), H_INIT_LEVEL)
  case "WATER_HABITAT" => structure = Habitat(WATER, Position (x, y), H_INIT_LEVEL)
  case "EARTH_HABITAT" => structure = Habitat(EARTH, Position (x, y), H_INIT_LEVEL)
  case "AIR_HABITAT" => structure = Habitat(AIR, Position (x, y), H_INIT_LEVEL)


  override val json: JsValue = _
  override val creature: Creature = _
}
