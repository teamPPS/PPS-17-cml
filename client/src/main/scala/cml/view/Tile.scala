package cml.view

import javafx.scene.canvas.Canvas
import play.api.libs.json._

trait Tile {

  def description: String
  def imageSprite: Canvas
  def json: JsValue
}

object Tile {

  def apply(description: String, imageSprite: Canvas, json: JsValue): Tile = TileImplementation(description, imageSprite, json)
  
  case class TileImplementation(override val description: String, override val imageSprite: Canvas, override val json: JsValue)
    extends Tile 
}

