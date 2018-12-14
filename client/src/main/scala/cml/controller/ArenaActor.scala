package cml.controller

import akka.actor.{Actor, ActorLogging, ActorRef}
import cml.controller.fx.ArenaViewController
import cml.controller.messages.ArenaRequest._
import cml.controller.messages.ArenaResponse.AttackSuccess
import cml.controller.messages.BattleResponse.RequireChallengerFailure
import cml.model.base.Creature
import javafx.application.Platform
import javafx.scene.image.ImageView

/**
  *@author Chiara Volonnino
  */

class ArenaActor extends Actor with ActorLogging {
  private var battleActor: ActorRef = _
  private var controller: ArenaViewController = _
  private var challengerCreature: Option[Creature] = _


  override def receive: Receive = {
    case ActorRefRequest(actor, creature) =>
      battleActor = actor
      challengerCreature = creature
      log.info("Creature  " + challengerCreature)
    case ControllerRefRequest(controllerValue) =>
      log.info("CONTROLLER -- " + controllerValue + "and ref is " + controller)
      controller = controllerValue
    case ChallengerCreatureRequire() => Platform.runLater(() => controller.challengeCreature(challengerCreature))
    case AttackRequest(value, protection) => battleActor ! AttackRequest(value, protection)
    case AttackSuccess(value, isProtected) =>
      Platform.runLater(() => controller.userLifeBar_(value, isProtected))
    case StopRequest() =>
      battleActor ! StopRequest()
      context.stop(self)
  }
}
