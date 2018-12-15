package cml.controller.messages

/**
  * Arena response message
  */

object ArenaResponse {

  sealed trait ArenaResponse

  /**
    * Response if users turn is correctly management
    * @param attackPower is user creature power attack
    * @param isProtection flag represent if user creature is protected or not
    * @param turnValue turn value
    */
  case class RequireTurnSuccess(attackPower: Int, isProtection:Boolean, turnValue: Int) extends ArenaResponse

  /**
    * Success response to receive attack
    * @param attackPower is user creature power attack
    * @param isProtected flag represent if user creature is protected or not
    * @param turnValue turn value
    */
  case class AttackSuccess(attackPower: Int, isProtected: Boolean, turnValue: Int) extends ArenaResponse
}
