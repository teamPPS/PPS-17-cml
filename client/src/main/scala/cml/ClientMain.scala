package cml

import akka.actor.{ActorSystem, Props}
import cml.controller.AuthenticationActor
import cml.controller.messages.AuthenticationRequest.Login

object ClientMain {
  def main(args: Array[String]): Unit ={

    //View
    //Controller
    val system = ActorSystem("mySystem")
    val authenticationActor = system.actorOf(Props[AuthenticationActor], "authenticationActor")
    authenticationActor ! Login("user", "qwe")
  }


}
