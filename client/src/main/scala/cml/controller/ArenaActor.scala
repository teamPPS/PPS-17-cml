package cml.controller

import akka.actor.{Actor, ActorLogging, ActorRef}
import cml.controller.fx.ArenaViewController
import cml.controller.messages.ArenaRequest._
import cml.controller.messages.ArenaResponse.AttackSuccess
import cml.model.base.Creature
import javafx.application.Platform

/**
  * That class implements the actor which manages the arena process
  *
  * @author Chiara Volonnino
  */

class ArenaActor extends Actor with ActorLogging {
  private var battleActor: ActorRef = _
  private var controller: ArenaViewController = _
  private var challengerCreature: Option[Creature] = _
  private var turn: Int = _


  override def receive: Receive = {
    case ActorRefRequest(actor, creature, turnValue) =>
      battleActor = actor
      challengerCreature = creature
      turn = turnValue
    case ControllerRefRequest(controllerValue) =>
      controller = controllerValue
      Platform.runLater(() => controller.turn_(turn))
    case ChallengerCreatureRequire() => Platform.runLater(() => controller.challengeCreature(challengerCreature))
    case AttackRequest(value, protection) => battleActor ! AttackRequest(value, protection)
    case AttackSuccess(value, isProtected, turnValue) =>
      Platform.runLater(() => controller.userLifeBar_(value, isProtected, turnValue))
    case StopRequest(scene) =>
      battleActor ! StopRequest(scene)
      context.stop(self)
  }
}
