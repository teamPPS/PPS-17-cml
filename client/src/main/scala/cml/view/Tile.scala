package cml.view

import javafx.scene.canvas.Canvas
import play.api.libs.json._

/**
  * @author ecavina
  * @author (edited by) Monica Gondolini
  */

trait BaseTile {
  def description: String
  def imageSprite: Canvas
}

trait Tile extends BaseTile {
  def json: JsValue
}

object BaseTile {

  def apply(description: String, imageSprite: Canvas): BaseTile = BaseTileImplementation(description, imageSprite)

  case class BaseTileImplementation(override val description: String, override val imageSprite: Canvas)
    extends BaseTile
}

object Tile {

  def apply(description: String, imageSprite: Canvas, json: JsValue): Tile = TileImplementation(description, imageSprite, json)
  
  case class TileImplementation(override val description: String, override val imageSprite: Canvas, override val json: JsValue)
    extends Tile 
}

