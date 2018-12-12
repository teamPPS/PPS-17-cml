package cml.controller

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSelection, Props}
import cml.controller.actor.utils.ActorUtils.ActorSystemInfo.system
import cml.controller.messages.ArenaRequest._
import cml.controller.messages.ArenaResponse.{AttackSuccess, RequireTurnSuccess}
import cml.controller.messages.BattleRequest._
import cml.controller.messages.BattleResponse.{ExistChallengerSuccess, RequireEnterInArenaSuccess}
import cml.model.base.Creature
import cml.utils.ViewConfig.ArenaWindow
import cml.view.ViewSwitch
import javafx.application.Platform
import javafx.scene.Scene

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
  var challengerCreature:  Option[Creature] = _

  private val selectedCreature: Option[Creature] = Creature.selectedCreature

  @throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    remoteActor = context.actorSelection("akka.tcp://CML@127.0.0.1:5150/user/RemoteActor")
    arenaActor = system.actorOf(Props(new ArenaActor()), "ArenaActor")
    println(selectedCreature)
    remoteActor ! RequireEnterInArena(selectedCreature)
  }

  override def postStop(): Unit = {
    //TODO: remember actorSystem.shutdown? is correct even so (also sin stop clause )
    log.info("Actor is stopped")
  }

  override def receive: Receive = {
    case SceneInfo(scene) => sceneContext = scene
    case RequireEnterInArenaSuccess() =>
      println("RequireEnterInArenaSuccess")
      remoteActor ! ExistChallenger()
    case ExistChallengerSuccess(userAndCreature) =>
      println("ExistChallengerSuccess"+userAndCreature)
      remoteActor ! ExitRequest()
      log.info("User in list - " + userAndCreature)
      myChallenge(userAndCreature)
      self ! SwitchInArenaRequest()
    case SwitchInArenaRequest() =>
      arenaActor ! ActorRefRequest(self)
      arenaActor ! ChallengerCreature(challengerCreature)
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

  private def myChallenge(userAndCreature: Map[ActorRef,  Option[Creature]]): Unit = {
    userAndCreature.foreach{ case (actor, creature) =>
        if(!actor.equals(self)) {
          challenger = actor
          challengerCreature = creature
        }
    }
    log.info("Im user: " + self + " and my challenger is - " + challenger+ "\n"
      + "My creature is: " + selectedCreature.get + " and my challenger's creature is - " + challengerCreature.get)
    _turn(userAndCreature)
  }

  private def _turn(userAndCreature:  Map[ActorRef,  Option[Creature]]): Unit = {
    if(userAndCreature.head._1 equals self) turn = 0
    else turn = 1
    log.info("My turn is: " + turn)
  }

  private def switchInArena(): Unit ={
    ViewSwitch.activate(ArenaWindow.path, sceneContext)
  }
}

