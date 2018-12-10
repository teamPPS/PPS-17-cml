package cml.controller.actor.utils

/**
  * This class utils to management battle turn
  *
  * @author Chiara Volonnino
  */

trait Turn {

  /**
    * Change actor turn
    * @param turn current turn
    * @return turn changed
    */
  def changeTurn(turn: Int): Int
}
case class TurnImpl() extends Turn{

  val First: Int = 0
  val Second: Int = 1

  def ini(turn: Int): Boolean = {
    turn.equals(First)
  }

  override def changeTurn(turn: Int): Int = {
    var turn_ : Int = First
    if (turn equals First) {
      turn_ = Second
      println("turn is " + turn_)
    }
    else {
      turn_ = First
      println("turn is " + turn_)
    }
    turn_
  }
}
