package cml.controller

import akka.actor.{Actor, ActorLogging, ActorRef}
import cml.controller.messages.ArenaRequest.{ActorRefRequest, AttackRequest, StopRequest}
import cml.controller.messages.ArenaResponse.AttackSuccess

class ArenaActor extends Actor with ActorLogging {
  private var battleActor: ActorRef = _
  private var _powerValue: Int = _

  override def receive: Receive = {
    case ActorRefRequest(actor) => battleActor = actor
    case AttackRequest(value) => battleActor ! AttackRequest(value)
    case AttackSuccess(value) => _powerValue = value
    case StopRequest() => battleActor ! StopRequest()
  }
    def powerValue_(): Double = _powerValue
}
