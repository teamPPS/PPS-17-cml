package cml.model.static_model
import cml.model.base._
import cml.utils.ModelConfig.Habitat._
import cml.utils.ModelConfig.Building._
import cml.utils.ModelConfig.Elements._
import cml.view.Tile
import play.api.libs.json.JsValue

/**
  * @author Monica Gondolini
  */
trait StaticStructures {
  def json: JsValue
  def getStructure: Structure
}

case class StaticStructure(t: Tile, x: Int, y: Int) extends  StaticStructures {

  var structure: Structure = _
    t.description match {
    case "FIRE_HABITAT" =>
      structure = Habitat(FIRE, Position (x, y), H_INIT_LEVEL)
      println ("habitat posizionato " + structure + "  " + t.json) //debug
    case "WATER_HABITAT" =>
      structure = Habitat(WATER, Position (x, y), H_INIT_LEVEL)
      println ("habitat posizionato " + structure + "  " + t.json) //debug
    case "EARTH_HABITAT" =>
      structure = Habitat(EARTH, Position (x, y), H_INIT_LEVEL)
      println ("habitat posizionato " + structure + "  " + t.json) //debug
    case "AIR_HABITAT" =>
      structure = Habitat(AIR, Position (x, y), H_INIT_LEVEL)
      println ("habitat posizionato " + structure + "  " + t.json) //debug
    case "FARM" =>
      structure = Farm (Position (x, y), B_INIT_LEVEL)
      println ("farm posizionato " + structure + "  " + t.json) //debug
    case "CAVE" =>
      structure = Cave (Position (x, y), B_INIT_LEVEL)
      println ("cave posizionato " + structure + "  " + t.json) //debug
    case "TERRAIN" => println ("terrain posizionato") //debug
    case _ => throw new NoSuchElementException
  }

  override def json: JsValue = t.json
  override def getStructure: Structure = structure

}