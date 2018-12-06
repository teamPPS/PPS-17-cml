package cml.controller

import cml.controller.messages.BattleRequest._
import cml.controller.messages.BattleResponse.{ExistChallengerSuccess, ExitSuccess, RequireEnterInArenaSuccess}
import akka.actor.{Actor, ActorRef, ActorSelection}
import cml.controller.messages.BattleRequest
import cml.utils.ViewConfig.ArenaWindow
import cml.view.ViewSwitch
import javafx.application.Platform
import javafx.scene.Scene

import scala.collection.mutable.ListBuffer

/**
  * This class implements battle actor and managements user battle
  *
  * @author Chiara Volonnino
  */

class BattleActor extends Actor {

  var remoteActor: ActorSelection = _
  var challenger: ActorRef = _
  var challengerActor: ActorSelection = _
  var sceneContext: Scene = _

  @throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    remoteActor = context.actorSelection("akka.tcp://CML@127.0.0.1:5150/user/RemoteActor")
    remoteActor ! RequireEnterInArena()
  }

  override def postStop(): Unit = {

  }

  override def receive: Receive = {
    case SceneInfo(scene) => sceneContext = scene
    case RequireEnterInArenaSuccess() => remoteActor ! ExistChallenger()
    case ExistChallengerSuccess(user) =>
      remoteActor ! ExitRequest()
      println("user in list - " + user)
      myChallenge(user)
      self ! BattleRequest.SwitchInArenaRequest()
      /*challengerActor = context.actorSelection(challenger.toString())
      challenger ! HelloChallenger("Hello my challenger im " + self)*/
    case SwitchInArenaRequest() => Platform.runLater(() => switchInArena())
  }

  private def myChallenge(user: ListBuffer[ActorRef]): Unit = {
    user.foreach(actor => {
      if(!actor.equals(self)) {
        challenger = actor
        println("Im user: " + self + " and my challenger is - " + challenger)
      }
    })
  }

  private def switchInArena(): Unit ={
    ViewSwitch.activate(ArenaWindow.path, sceneContext)
  }
}

/*object BattleActor {
// TODO: delete this main (already exist in battleController)
  def main(args: Array[String])  {
    val configFile = getClass.getClassLoader.getResource(Path).getFile
    val config = ConfigFactory.parseFile(new File(configFile))
    val system = ActorSystem(Context, config)
    val battleActor = system.actorOf(Props[BattleActor], name=Name)
    println("------ battleActor is ready")
  }
}*/

