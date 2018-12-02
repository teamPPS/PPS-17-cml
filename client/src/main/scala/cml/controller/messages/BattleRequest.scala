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
    * @param actorRef user actorRef
    */
  case class RequireChallenger() extends BattleRequest

}
