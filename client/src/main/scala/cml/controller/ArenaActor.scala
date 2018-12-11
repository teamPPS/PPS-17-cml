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
  private var _powerValue: Int = _
  private var controller: ArenaViewController = _

  override def receive: Receive = {
    case ActorRefRequest(actor) => battleActor = actor
    case AttackRequest(value) => battleActor ! AttackRequest(value)
    case AttackSuccess(value) => _powerValue = value
      Platform.runLater(() => controller.userLifeBar_(_powerValue))
    case ControllerRefRequest(controllerValue) =>
      log.info("CONTROLLER -- " + controllerValue + "and ref is " + controller)
      controller = controllerValue
    case StopRequest() =>
      battleActor ! StopRequest()
      context.stop(self)
  }
    def powerValue_(): Int = _powerValue
}
