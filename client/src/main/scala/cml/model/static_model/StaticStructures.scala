package cml.model.static_model

import cml.model.base.Habitat.Habitat
import cml.model.base.{Cave, Farm, Position, Structure}
import cml.utils.{BuildingJson, HabitatJson}
import cml.utils.ModelConfig.Habitat.H_INIT_LEVEL
import cml.utils.ModelConfig.Building.B_INIT_LEVEL
import cml.utils.ModelConfig.Elements.{AIR, EARTH, FIRE, WATER}
import cml.utils.ModelConfig.StructureType.{AIR_HABITAT, CAVE, EARTH_HABITAT, FARM, FIRE_HABITAT, WATER_HABITAT}
import cml.view.Tile
import play.api.libs.json.JsValue

/**
  * Trait to generate Static Structures
  * @author Monica Gondolini
  */
trait StaticStructures {

  /**
    * Creates a json
    * @return a json
    */
  def json: JsValue

  /**
    * Get the type of structure
    * @return structure
    */
  def getStructure: Structure
}

case class StaticStructure(t: Tile, x: Int, y: Int) extends  StaticStructures {

  var structure: Structure = _
  var jsonStructure: JsValue = _

  t.description match {
    case FIRE_HABITAT =>
      structure = Habitat(FIRE, Position (x, y), H_INIT_LEVEL)
      jsonStructure = HabitatJson(FIRE, H_INIT_LEVEL, Position(x, y)).json
    case WATER_HABITAT =>
      structure = Habitat(WATER, Position (x, y), H_INIT_LEVEL)
      jsonStructure = HabitatJson(WATER, H_INIT_LEVEL, Position(x, y)).json
    case EARTH_HABITAT =>
      structure = Habitat(EARTH, Position (x, y), H_INIT_LEVEL)
      jsonStructure = HabitatJson(EARTH, H_INIT_LEVEL, Position(x, y)).json
    case AIR_HABITAT =>
      structure = Habitat(AIR, Position (x, y), H_INIT_LEVEL)
      jsonStructure = HabitatJson(AIR, H_INIT_LEVEL, Position(x, y)).json
    case FARM =>
      structure = Farm(Position (x, y), B_INIT_LEVEL)
      jsonStructure = BuildingJson(FARM, B_INIT_LEVEL, Position(x, y)).json
    case CAVE =>
      structure = Cave(Position (x, y), B_INIT_LEVEL)
      jsonStructure = BuildingJson(CAVE, B_INIT_LEVEL, Position(x, y)).json
    case _ => throw new NoSuchElementException
  }


  override def json: JsValue = jsonStructure
  override def getStructure: Structure = structure

}