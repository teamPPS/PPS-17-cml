package cml.controller

import cml.controller.messages.BattleRequest._
import cml.controller.messages.BattleResponse.{ExistChallengerSuccess, RequireEnterInArenaSuccess}
import akka.actor.{Actor, ActorLogging, ActorRef, ActorSelection, Props}
import cml.controller.messages.ArenaRequest.{ActorRefRequest, AttackRequest, StopRequest}
import cml.controller.messages.BattleRequest
import cml.utils.ViewConfig.ArenaWindow
import cml.controller.actor.utils.ActorUtils.ActorSystemInfo.system
import cml.view.ViewSwitch
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
  var challengerActor: ActorSelection = _
  var sceneContext: Scene = _
  var turn: Int = _
  val arenaActor: ActorRef = system.actorOf(Props(new ArenaActor()), "ArenaActor")

  @throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    remoteActor = context.actorSelection("akka.tcp://CML@127.0.0.1:5150/user/RemoteActor")
    remoteActor ! RequireEnterInArena()
  }

  override def postStop(): Unit = {
    //TODO: remember actorSystem.shutdown? is correct even so (also sin stop clause )
    log.info("Actor is stopped")
  }

  override def receive: Receive = {
    case SceneInfo(scene) => sceneContext = scene
    case RequireEnterInArenaSuccess() => remoteActor ! ExistChallenger() //TODO: add progress indicator
    case ExistChallengerSuccess(user) =>
      remoteActor ! ExitRequest()
      log.info("user in list - " + user)
      myChallenge(user)
      self ! BattleRequest.SwitchInArenaRequest()
    case SwitchInArenaRequest() =>
      arenaActor ! ActorRefRequest(self)
      Platform.runLater(() => switchInArena())
    case AttackRequest(attackPower) => log.info("attack " + attackPower )
    case StopRequest() => context.stop(self)
  }

  private def myChallenge(user: ListBuffer[ActorRef]): Unit = {
    user.foreach(actor => if(!actor.equals(self)) challenger = actor)
    log.info("Im user: " + self + " and my challenger is - " + challenger)
    challenge_()
    _turn(user)
  }

  private def _turn(user: ListBuffer[ActorRef]): Unit = {
    if(user.head equals self) turn = 0
    else turn = 1
    log.info("My turn is: " + turn)
  }

  private def challenge_(): Unit ={
    challengerActor = context.actorSelection(challenger.path)
    //println("actor challeng: " + challengerActor + " ref chal " + challenger)
    //challenger ! HelloChallenger("Hello my challenger im " + self)
  }

  private def switchInArena(): Unit ={
    ViewSwitch.activate(ArenaWindow.path, sceneContext)
  }
}

