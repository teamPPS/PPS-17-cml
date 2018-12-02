package cml.controller.messages

/**
  * Battle request messages
  *
  * @author Chiara Volonnino
  */

object BattleRequest {

  sealed trait BattleRequest

  /**
    * Request to challenger user
    */
  case class RequireChallenger() extends BattleRequest

}
