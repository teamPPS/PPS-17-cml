package cml.controller

import akka.actor.Actor
import cml.controller.messages.ArenaResponse.ExitSuccess
import cml.controller.messages.BattleRequest._
import cml.controller.messages.BattleResponse.RequireChallengerSuccess

class BattleActor extends Actor {

  @throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    val remoteActor = context.actorSelection("akka.tcp://CML@127.0.0.1:5150/user/RemoteActor")
    println("contex -> " + context)
    remoteActor ! RequireChallenger()
  }

  override def receive: Receive = {
    case RequireChallengerSuccess() => println("Your request is success delivery")
    case ExitSuccess() => println("Exit success delivery")
  }

}
