package cml.controller

import akka.actor.{Actor, ActorLogging, ActorRef}
import cml.controller.fx.ArenaViewController
import cml.controller.messages.ArenaRequest._
import cml.controller.messages.ArenaResponse.AttackSuccess
import cml.controller.messages.BattleRequest.NotifierExit
import cml.controller.messages.BattleResponse.NotifierExitSuccess
import cml.model.base.Creature
import javafx.application.Platform

import scala.collection.mutable.ListBuffer

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
  private var tmpUserList: ListBuffer[ActorRef] = new ListBuffer[ActorRef]

  override def receive: Receive = {
    case ActorRefRequest(actor, challenger,creature, turnValue) =>
      battleActor = actor
      challengerCreature = creature
      turn = turnValue
      tmpUserList += actor
      tmpUserList += challenger
    case ControllerRefRequest(controllerValue) =>
      controller = controllerValue
      Platform.runLater(() => controller.turn_(turn))
    case ChallengerCreatureRequire() => Platform.runLater(() => controller.challengeCreature(challengerCreature))
    case AttackRequest(value, protection) => battleActor ! AttackRequest(value, protection)
    case AttackSuccess(value, isProtected, turnValue) =>
      Platform.runLater(() => controller.userLifeBar_(value, isProtected, turnValue))
    case NotifierExit(actor) =>
      //tmpUserList -= actor
      tmpUserList.foreach( actorInList => actorInList ! NotifierExitSuccess())
      if(actor equals battleActor) context.stop(self)
    case NotifierExitSuccess() =>
      Platform.runLater(() => controller.exitOption())
    case StopRequest(scene) =>
      battleActor ! StopRequest(scene)
  }
}
