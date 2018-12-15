package cml.controller

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSelection, Props}
import cml.controller.actor.utils.ActorUtils.ActorSystemInfo.system
import cml.controller.actor.utils.ActorUtils.RemoteActorInfo
import cml.controller.messages.ArenaRequest._
import cml.controller.messages.ArenaResponse.{AttackSuccess, RequireTurnSuccess}
import cml.controller.messages.BattleRequest._
import cml.controller.messages.BattleResponse.{ExistChallengerSuccess, NotifierExitSuccess, RequireEnterInArenaSuccess}
import cml.model.base.Creature
import cml.utils.ViewConfig.{ArenaWindow, VillageWindow}
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

  private var remoteActor: ActorSelection = _
  private var challenger: ActorRef = _
  private var sceneContext: Scene = _
  private var turn: Int = _
  private var arenaActor: ActorRef = _
  private var challengerCreature: Option[Creature] = _
  private val selectedCreature: Option[Creature] = Creature.selectedCreature

  @throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    remoteActor = context.actorSelection(RemoteActorInfo.Path)
    arenaActor = system.actorOf(Props(new ArenaActor()), "ArenaActor")
    remoteActor ! RequireEnterInArena()
  }

  override def postStop(): Unit = {
    //TODO: remember actorSystem.shutdown? is correct even so (also sin stop clause )
    log.info("Actor is stopped")
  }

  override def receive: Receive = {
    case SceneInfo(scene) => sceneContext = scene
    case RequireEnterInArenaSuccess() => remoteActor ! ExistChallenger()
    case ExistChallengerSuccess(user) =>
      remoteActor ! ExitRequest()
      myChallenge(user)
    case CreatureRequire(creature) =>
      challengerCreature = creature
      self ! SwitchInArenaRequest()
    case SwitchInArenaRequest() =>
      arenaActor ! ActorRefRequest(self, challengerCreature, turn)
      Platform.runLater(() => switchInArena())
    case AttackRequest(attackPower, protection) => remoteActor ! RequireTurnRequest(attackPower, protection, turn)
    case RequireTurnSuccess(attackPower, protection, turnValue) =>
      log.info("Turn" + turnValue)
      challenger ! AttackSuccess(attackPower, protection, turnValue)
    case AttackSuccess(attackPower, isProtected, turnValue) =>
      log.info("Attack " + attackPower + " is protected " + isProtected)
      arenaActor ! AttackSuccess(attackPower, isProtected, turnValue)
    case StopRequest(scene) =>
      remoteActor ! NotifierExit(self)
      Platform.runLater(() => ViewSwitch.activate(VillageWindow.path, scene))
      context.stop(self)
    case NotifierExitSuccess() => arenaActor ! NotifierExitSuccess()
    case _ =>
  }

  private def myChallenge(user: ListBuffer[ActorRef]): Unit = {
    user.foreach{ actor => if(!actor.equals(self)) challenger = actor}
    challenger ! CreatureRequire(selectedCreature)
    log.info("Im user: " + self + " and my challenger is - " + challenger)
    _turn(user)
  }

  private def _turn(user:  ListBuffer[ActorRef]): Unit = {
    if(user.head equals self) turn = 0
    else turn = 1
    log.info("My turn is: " + turn)
  }

  private def switchInArena(): Unit ={
    ViewSwitch.activate(ArenaWindow.path, sceneContext)
  }
}

