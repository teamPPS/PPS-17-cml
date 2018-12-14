package cml.controller.messages

import akka.actor.ActorRef
import cml.controller.fx.ArenaViewController
import cml.model.base.Creature

/**
  * Arena request message
  *
  * @author Chiara Volonnino
  */
object ArenaRequest {

  sealed trait ArenaRequest

  /**
    * Request for know the remote actor
    *
    * @param actor is actor battle ref
    * @param challengeCreature is challenge creature image
    * @param turnValue is user turn
    */
  case class ActorRefRequest(actor: ActorRef, challengeCreature: Option[Creature], turnValue: Int) extends ArenaRequest


  case class ChallengerCreatureRequire() extends ArenaRequest

  /**
    * Request for attack challenger
    * @param attackPower is user creature power attack
    * @param isProtected flag represent if user creature is protected or not
    */
  case class AttackRequest(attackPower: Int, isProtected: Boolean) extends ArenaRequest

  /**
    * Request for management turn in battle
    * @param attackPower creature power attack
    * @param isProtected flag represent if user creature is protected or not
    * @param turn actual turn
    */
  case class RequireTurnRequest(attackPower: Int, isProtected: Boolean, turn: Int) extends ArenaRequest

  /**
    * Request for ArenaViewController identifier
    * @param controller class identifier
    */
  case class ControllerRefRequest(controller: ArenaViewController) extends ArenaRequest

  /**
    * Request for stopping Actor
    */
  case class StopRequest() extends ArenaRequest

}
