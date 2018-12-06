package cml.controller

import java.io.File

import cml.controller.messages.BattleRequest.{ExistChallenger, ExitRequest, RequireEnterInArena}
import cml.controller.messages.BattleResponse.{ExistChallengerSuccess, ExitSuccess, RequireEnterInArenaSuccess}
import cml.controller.actor.utils.ActorUtils.BattleActorInfo._
import akka.actor.{Actor, ActorRef, ActorSelection, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

import scala.collection.mutable.ListBuffer

/**
  * This class implements battle actor and managements user battle
  *
  * @author Chiara Volonnino
  */

class BattleActor extends Actor {

  var remoteActor: ActorSelection = _
  var challenger: ActorRef = _

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
    case ExistChallengerSuccess(user) =>
      remoteActor ! ExitRequest()
      println("user in list - " + user)
      myChallenge(user)

  }

  private def myChallenge(user: ListBuffer[ActorRef]): Unit = {
    user.foreach(actor => {
      if(!actor.equals(self)) {
        challenger = actor
        println("Im user: " + self + " and my challenger is - " + challenger)
      }
    })
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

