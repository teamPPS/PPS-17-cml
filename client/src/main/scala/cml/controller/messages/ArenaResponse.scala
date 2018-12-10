package cml.controller.messages

/**
  * Arena response message
  */

object ArenaResponse {

  sealed trait ArenaResponse

  case class HelloChallengerSuccess() extends ArenaResponse

  /**
    * Success response to receive attack
    */
  case class AttackSuccess() extends ArenaResponse

  case class RequireTurnSuccess(attackPower: Int, turn: Int) extends ArenaResponse
}
