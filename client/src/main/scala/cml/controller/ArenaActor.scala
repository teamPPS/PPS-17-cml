package cml.controller

/**
  * Class that implements the actor which manages the arena interaction with user
  *
  * @author Chiara Volonnino
  */

import akka.actor.{Actor, ActorLogging, ActorRef}
import cml.controller.messages.ArenaRequest.{ActorRefRequest, AttackRequest}

class ArenaActor extends Actor with ActorLogging {

  private var battleActor: ActorRef = _

  override def receive: Receive = {
    case ActorRefRequest(actor) => battleActor = actor
    case AttackRequest(value) =>
      println("attack value " + value)
      battleActor ! AttackRequest(value)
  }
}
