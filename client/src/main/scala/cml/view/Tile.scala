package cml.view

import javafx.scene.canvas.Canvas

trait Tile {

  def description: String
  def imageSprite: Canvas
  
}

object Tile {

  def apply(description: String, imageSprite: Canvas): Tile = TileImplementation(description, imageSprite)
  
  case class TileImplementation(override val description: String, override val imageSprite: Canvas)
    extends Tile
}

