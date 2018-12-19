package cml.view.BattleRule

/**
  * This class utils for management battle turn
  *
  * @author Chiara Volonnino
  */

trait TurnManagement {

  /**
    * Initialize turn value
    */
  def initialization(): Unit

  /**
    * Management users turn
    * @param turn current turn
    */
  def changeTurn(turn: Int):Int

}

/**
  * This class implements trait TurnManagement
  */
case class TurnManagementImpl() extends TurnManagement {

  private val First: Int = 0
  private val Second: Int = 1
  private var turn_ : Int = _
  private var isFirst: Boolean = _

  override def initialization(): Unit = {
    turn_ = 0
    isFirst = true
  }

  override def changeTurn(turn: Int): Int = {
    if(isFirst && turn.equals(First)) {
      turn_ = Second
      isFirst = false
    }
    if(!isFirst && turn.equals(Second)) {
      turn_ = First
      isFirst = true
    }
    turn_
  }

}
