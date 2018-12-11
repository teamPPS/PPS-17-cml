package cml.controller

import cml.controller.messages.BattleRequest._
import cml.controller.messages.BattleResponse.{ExistChallengerSuccess, RequireEnterInArenaSuccess}
import akka.actor.{Actor, ActorLogging, ActorRef, ActorSelection, Props}
import cml.controller.messages.ArenaRequest.{ActorRefRequest, AttackRequest, RequireTurnRequest, StopRequest}
import cml.utils.ViewConfig.ArenaWindow
import cml.controller.actor.utils.ActorUtils.ActorSystemInfo.system
import cml.controller.messages.ArenaResponse.{AttackSuccess, RequireTurnSuccess}
import cml.view.ViewSwitch
import cml.view.utils.ProgressView
import javafx.application.Platform
import javafx.scene.Scene

import scala.collection.mutable.ListBuffer

/**
  * This class implements battle actor and managements user battle
  *
  * @author Chiara Volonnino
  */

class BattleActor extends Actor with ActorLogging {

  var remoteActor: ActorSelection = _
  var challenger: ActorRef = _
  var sceneContext: Scene = _
  var turn: Int = _
  var arenaActor: ActorRef = _

  @throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    remoteActor = context.actorSelection("akka.tcp://CML@127.0.0.1:5150/user/RemoteActor")
    arenaActor = system.actorOf(Props(new ArenaActor()), "ArenaActor")
    remoteActor ! RequireEnterInArena()
  }

  override def postStop(): Unit = {
    //TODO: remember actorSystem.shutdown? is correct even so (also sin stop clause )
    log.info("Actor is stopped")
  }

  override def receive: Receive = {
    case SceneInfo(scene) => sceneContext = scene
    case RequireEnterInArenaSuccess() =>
      remoteActor ! ExistChallenger()
    case ExistChallengerSuccess(user) =>
      remoteActor ! ExitRequest()
      log.info("User in list - " + user)
      myChallenge(user)
      self ! SwitchInArenaRequest()
    case SwitchInArenaRequest() =>
      arenaActor ! ActorRefRequest(self)
      Platform.runLater(() => switchInArena())
    case AttackRequest(attackPower) => remoteActor ! RequireTurnRequest(attackPower, turn)
    case RequireTurnSuccess(attackPower, turnValue) =>
      log.info("Turn" + turnValue)
      if(turnValue equals turn) challenger ! AttackSuccess(attackPower)
    case AttackSuccess(attackPower) =>
      log.info("Attack " + attackPower)
      arenaActor ! AttackSuccess(attackPower)
    case StopRequest() => context.stop(self)
  }

  private def myChallenge(user: ListBuffer[ActorRef]): Unit = {
    user.foreach(actor => if(!actor.equals(self)) challenger = actor)
    log.info("Im user: " + self + " and my challenger is - " + challenger)
    _turn(user)
  }

  private def _turn(user: ListBuffer[ActorRef]): Unit = {
    if(user.head equals self) turn = 0
    else turn = 1
    log.info("My turn is: " + turn)
  }

  private def switchInArena(): Unit ={
    ViewSwitch.activate(ArenaWindow.path, sceneContext)
  }
}

