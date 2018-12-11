package cml.controller.messages

import akka.actor.ActorRef
import cml.controller.fx.ArenaViewController

/**
  * Arena request message
  *
  * @author Chiara Volonnino
  */
object ArenaRequest {

  sealed trait ArenaRequest

  /**
    * Request for know the remote actor
    * @param actor is actor battle ref
    */
  case class ActorRefRequest(actor: ActorRef) extends ArenaRequest

  /**
    * Request for stopping Actor
    */
  case class StopRequest() extends ArenaRequest

  /**
    * Request for attack the challenger
    */
  case class AttackRequest(attackPower: Int) extends ArenaRequest

  case class RequireTurnRequest(attackPower: Int, turn: Int) extends ArenaRequest

  case class ControllerRefRequest(controller: ArenaViewController) extends ArenaRequest

}
