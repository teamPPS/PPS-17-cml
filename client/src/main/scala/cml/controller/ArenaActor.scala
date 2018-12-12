package cml.controller

import akka.actor.{Actor, ActorLogging, ActorRef}
import cml.controller.fx.ArenaViewController
import cml.controller.messages.ArenaRequest.{ActorRefRequest, AttackRequest, ControllerRefRequest, StopRequest}
import cml.controller.messages.ArenaResponse.AttackSuccess
import javafx.application.Platform

/**
  *
  */

class ArenaActor extends Actor with ActorLogging {
  private var battleActor: ActorRef = _
  private var controller: ArenaViewController = _

  override def receive: Receive = {
    case ActorRefRequest(actor) => battleActor = actor
    case AttackRequest(value, protection) => battleActor ! AttackRequest(value, protection)
    case AttackSuccess(value, isProtected) =>
      Platform.runLater(() => {
        controller.userLifeBar_(value, isProtected)
      })
    case ControllerRefRequest(controllerValue) => controller = controllerValue
    case StopRequest() =>
      battleActor ! StopRequest()
      context.stop(self)
  }
}
