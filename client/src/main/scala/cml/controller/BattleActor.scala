package cml.controller

import java.io.File

import cml.controller.messages.BattleRequest.{ExistChallenger, ExitRequest, RequireEnterInArena}
import cml.controller.messages.BattleResponse.{ExistChallengerSuccess, ExitSuccess, RequireEnterInArenaSuccess}
import cml.controller.actor.utils.ActorUtils.BattleActorInfo._
import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

/**
  * This class implements battle actor and managements user battle
  *
  * @author Chiara Volonnino
  */

class BattleActor extends Actor {

  var remoteActor: ActorSelection = _

  @throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    remoteActor = context.actorSelection("akka.tcp://CML@127.0.0.1:5150/user/RemoteActor")
    remoteActor ! RequireEnterInArena()
  }

  override def postStop(): Unit = {

  }

  override def receive: Receive = {
    case RequireEnterInArenaSuccess() =>
      println("Your request is success delivery")
      remoteActor ! ExistChallenger()
    case ExistChallengerSuccess(exist, user) =>
      if (exist) remoteActor ! ExitRequest()
      println("response . " + exist + "user in list" + user)
    case ExitSuccess() => println("Exit success delivery")
  }

}

object BattleActor {
// TODO: delete this main (already exist in battleController)
  def main(args: Array[String])  {
    val configFile = getClass.getClassLoader.getResource(Path).getFile
    val config = ConfigFactory.parseFile(new File(configFile))
    val system = ActorSystem(Context, config)
    val battleActor = system.actorOf(Props[BattleActor], name=Name)
    println("------ battleActor is ready")
  }
}

