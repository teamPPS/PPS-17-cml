package cml.controller.messages

import akka.actor.ActorRef
import scala.collection.mutable.ListBuffer

/**
  * Battle response message
  *
  * @author Chiara Volonnino
  */

object BattleResponse {

  sealed trait BattleResponse

  /**
    * Success response to add user into a list for require enter in battle arena
    */
  case class RequireEnterInArenaSuccess() extends BattleResponse

  case class RequireChallengerFailure() extends BattleResponse

  /**
    * Success response for require challenger
    *
    * @param exist response true if exist challenger
    * @param user user list for battle
    */
  case class ExistChallengerSuccess(user: ListBuffer[ActorRef]) extends BattleResponse

  /**
    * Success response for delete user into a list of wait challenger
    */
  case class ExitSuccess() extends BattleResponse


}
