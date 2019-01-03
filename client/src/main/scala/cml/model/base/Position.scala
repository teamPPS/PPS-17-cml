package cml.model.base

import play.api.libs.json.Json

/**
  * Represents a position in the village grid
 *
  *  @param x coordinate
  *  @param y coordinate
  *  @author Monica Gondolini
  */

case class Position(x: Int, y: Int)

object Position {
  implicit val positionFormat = Json.format[Position]
}



