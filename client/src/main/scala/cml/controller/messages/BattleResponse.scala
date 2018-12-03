package cml.controller.messages

import java.io.Serializable

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
