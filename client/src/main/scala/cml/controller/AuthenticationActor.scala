package cml.controller

import akka.actor.Actor
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import cml.controller.messages.AuthenticationRequest.{Login, Logout, Register}

/**
  *  Actor for user authentication
  *
  * @author Monica Gondolini
  */

class AuthenticationActor extends Actor{

   // to get an implicit ExecutionContext into scope
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = context.dispatcher


  override def receive: Receive = {
    case Login(username,password) => {
      println(s"sending login request from username:$username with password:$password")

    } //connessione server
    case Register(username,password) => ???
    case Logout(username) => ???
  }
}
