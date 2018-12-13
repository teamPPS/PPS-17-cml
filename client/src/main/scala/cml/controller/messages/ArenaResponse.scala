package cml.controller.messages

/**
  * Arena response message
  */

object ArenaResponse {

  sealed trait ArenaResponse

  case class HelloChallengerSuccess() extends ArenaResponse

  case class RequireTurnSuccess(attackPower: Int, isProtection:Boolean, turn: Int) extends ArenaResponse

  /**
    * Success response to receive attack
    */
  case class AttackSuccess(attackPower: Int, isProtected: Boolean) extends ArenaResponse
}
