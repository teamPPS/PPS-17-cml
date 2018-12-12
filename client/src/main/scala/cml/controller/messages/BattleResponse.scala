package cml.controller.messages

import akka.actor.ActorRef
import cml.model.base.Creature

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
    * @param user user list for battle
    */
  case class ExistChallengerSuccess(userAndCreature: Map[ActorRef,  Option[Creature]]) extends BattleResponse

  /**
    * Success response for delete user into a list of wait challenger
    */
  case class ExitSuccess() extends BattleResponse


}
