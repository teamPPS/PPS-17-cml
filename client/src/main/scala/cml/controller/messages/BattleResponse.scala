package cml.controller.messages

/**
  * Battle response message
  *
  * @author Chiara Volonnino
  */

object BattleResponse {

  sealed trait BattleResponse

  case class RequireChallenger() extends BattleResponse

}
