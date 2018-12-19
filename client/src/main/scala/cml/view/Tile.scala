package cml.view

import javafx.scene.canvas.Canvas
import play.api.libs.json._

/**
  * This trait managements tile base
  * @author ecavina
  * @author (edited by) Monica Gondolini
  */
trait BaseTile {

  /**
    * To know tile description
    * @return tile description
    */
  def description: String

  /**
    * To define canvas image for all elements
    * @return canvas image
    */
  def imageSprite: Canvas
}

/**
  * Trait describe Tile
  */
trait Tile extends BaseTile {
  /**
    * Define a json value for particular tile
    * @return json value for a tile
    */
  def json: JsValue
}

/**
  * Object BaseTile
  */
object BaseTile {

  def apply(description: String, imageSprite: Canvas): BaseTile = BaseTileImplementation(description, imageSprite)

  case class BaseTileImplementation(override val description: String, override val imageSprite: Canvas)
    extends BaseTile
}

/**
  * Object Tile
  */
object Tile {

  def apply(description: String, imageSprite: Canvas, json: JsValue): Tile = TileImplementation(description, imageSprite, json)

  case class TileImplementation(override val description: String, override val imageSprite: Canvas, override val json: JsValue)
    extends Tile
}

