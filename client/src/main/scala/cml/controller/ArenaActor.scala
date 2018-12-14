package cml.controller

import akka.actor.{Actor, ActorLogging, ActorRef}
import cml.controller.fx.ArenaViewController
import cml.controller.messages.ArenaRequest._
import cml.controller.messages.ArenaResponse.AttackSuccess
import cml.model.base.Creature
import javafx.application.Platform
import javafx.scene.image.ImageView

/**
  *@author Chiara Volonnino
  */

class ArenaActor extends Actor with ActorLogging {
  private var battleActor: ActorRef = _
  private var controller: ArenaViewController = _
  private var challengerCreature: Creature = _


  override def receive: Receive = {
    case ActorRefRequest(actor) => battleActor = actor
    case ChallengerCreature(creature) =>
      challengerCreature = creature.get
      log.info("Creture: " + challengerCreature)
      Platform.runLater(() => controller.challengeCreature(challengerCreature))
    case ControllerRefRequest(controllerValue) =>
      log.info("CONTROLLER -- " + controllerValue + "and ref is " + controller)
      controller = controllerValue
    case AttackRequest(value, protection) => battleActor ! AttackRequest(value, protection)
    case AttackSuccess(value, isProtected) =>
      Platform.runLater(() => controller.userLifeBar_(value, isProtected))
    case ControllerRefRequest(controllerValue) => controller = controllerValue
    case StopRequest() =>
      battleActor ! StopRequest()
      context.stop(self)
  }
}
