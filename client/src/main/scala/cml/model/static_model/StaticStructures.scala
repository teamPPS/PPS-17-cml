package cml.model.static_model
import cml.model.base._
import cml.utils.ModelConfig.Habitat._
import cml.utils.ModelConfig.Building._
import cml.utils.ModelConfig.Elements._
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

  t.description match {
    case "FIRE_HABITAT" => structure = Habitat(FIRE, Position (x, y), H_INIT_LEVEL) //TODO mettere stringhe in varibili
    case "WATER_HABITAT" => structure = Habitat(WATER, Position (x, y), H_INIT_LEVEL)
    case "EARTH_HABITAT" => structure = Habitat(EARTH, Position (x, y), H_INIT_LEVEL)
    case "AIR_HABITAT" => structure = Habitat(AIR, Position (x, y), H_INIT_LEVEL)
    case "FARM" => structure = Farm(Position (x, y), B_INIT_LEVEL)
    case "CAVE" => structure = Cave(Position (x, y), B_INIT_LEVEL)
    case _ => throw new NoSuchElementException
  }

  override def json: JsValue = t.json
  override def getStructure: Structure = structure

}