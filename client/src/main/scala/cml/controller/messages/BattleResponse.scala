package cml.controller.messages

import akka.actor.ActorRef
import cml.model.base.Creature

import scala.collection.mutable.ListBuffer

/**
  * Battle response message
  *
  * @author Chiara Volonnino
  */

object BattleResponse {

  sealed trait BattleResponse


  /**
    * Success response to add user into a list to require enter in battle arena
    */
  case class RequireEnterInArenaSuccess() extends BattleResponse

  /**
    *  Failure response to wait for a challenger()
    */
  case class RequireChallengerFailure() extends BattleResponse

  /**
    * Success response for require challenger
    *
    * @param user user list
    */
  case class ExistChallengerSuccess(user: ListBuffer[ActorRef]) extends BattleResponse

  /**
    * Success response for delete user into a list of wait challenger
    */
  case class ExitSuccess() extends BattleResponse

}
