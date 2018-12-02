package cml.controller.messages

/**
  * Battle response message
  *
  * @author Chiara Volonnino
  */

object BattleResponse {

  sealed trait BattleResponse

  case class RequireChallengerSuccess() extends BattleResponse
  case class RequireChallengerFailure() extends BattleResponse

}
