package cml.controller

import akka.actor.{Actor, ActorLogging, ActorRef}
import cml.controller.fx.ArenaViewController
import cml.controller.messages.ArenaRequest._
import cml.controller.messages.ArenaResponse.AttackSuccess
import cml.model.base.Creature
import cml.utils.ModelConfig.Creature.{DRAGON, GOLEM, GRIFFIN, WATERDEMON}
import cml.utils.ModelConfig.CreatureImage.{griffinImage, golemImage, dragonImage, waterdemonImage}
import javafx.application.Platform

/**
  *@author Chiara Volonnino
  */

class ArenaActor extends Actor with ActorLogging {
  private var battleActor: ActorRef = _
  private var controller: ArenaViewController = _
  private var challengerCreature:  Option[Creature] = _


  override def receive: Receive = {
    case ActorRefRequest(actor) => battleActor = actor
    case ChallengerCreature(creature) =>
      challengerCreature = creature
      Platform.runLater(() => setChallengerCreatureImage(challengerCreature.get, controller))
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

  private def setChallengerCreatureImage(creature: Creature, controller: ArenaViewController): Unit = {
    creature.creatureType match {
      case DRAGON => controller.enemyCreature setImage dragonImage
      case GOLEM =>  controller.enemyCreature setImage golemImage
      case GRIFFIN =>  controller.enemyCreature setImage griffinImage
      case WATERDEMON =>  controller.enemyCreature setImage waterdemonImage
    }
  }
}
