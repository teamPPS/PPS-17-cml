package cml.controller

/**
  * Class that implements the actor which manages the arena interaction with user
  *
  * @author Chiara Volonnino
  */

import akka.actor.{Actor, ActorLogging, ActorRef}
import cml.controller.messages.ArenaRequest.{ActorRefRequest, AttackRequest, StopRequest}

class ArenaActor extends Actor with ActorLogging {

  private var battleActor: ActorRef = _

  override def receive: Receive = {
    case ActorRefRequest(actor) =>
      log.info("Receive ActorRef: " + actor)
      battleActor = actor
    case AttackRequest(value) => battleActor ! AttackRequest(value)
    case StopRequest() => battleActor ! StopRequest()
  }
}
