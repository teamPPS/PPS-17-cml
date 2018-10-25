package cml.controller

import akka.actor.{Actor, ActorLogging}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import cml.controller.messages.AuthenticationRequest.{Login, Logout, Register}

import scala.concurrent.Future
import scala.util.{Failure, Success}


/**
  *  Actor for user authentication
  *
  * @author Monica Gondolini
  */

class AuthenticationActor extends Actor   with ActorLogging{

  implicit val materializer: ActorMaterializer = ActorMaterializer(ActorMaterializerSettings(context.system))
  implicit val executionContext =  context.dispatcher
  val http = Http(context.system)

//  val authorization = headers.Authorization(BasicHttpCredentials("user", "pass")) //autorizzazione per la connessione al server con credenziali

  override def receive: Receive = {
    case Login(username,password) => {
      println(s"sending login request from username:$username with password:$password")

      val responseFuture: Future[HttpResponse] = http.singleRequest(HttpRequest(
        uri = Uri("http://akka.io"),
        method = HttpMethods.POST,
//        headers = List(authorization), //abilitare se è presente una connessione con credenziali al server
        entity = HttpEntity(username+password), //trovare altro modo
        protocol = HttpProtocols.`HTTP/1.1`)
      )

      responseFuture
        .onComplete {
          case Success(res) => println(res)
          case Failure(_)   => sys.error("something wrong")
        }
    }
    case Register(username,password) => {
      val responseFuture: Future[HttpResponse] = http.singleRequest(HttpRequest(
        uri = Uri("http://akka.io"),
        method = HttpMethods.POST,
//        headers = List(authorization), //abilitare se è presente una connessione con credenziali al server
        entity = HttpEntity(username+password), //trovare altro modo
        protocol = HttpProtocols.`HTTP/1.1`)
      )

      responseFuture
        .onComplete {
          case Success(res) => println(res)
          case Failure(_)   => sys.error("something wrong")
        }
    }
    case Logout(username) => ??? //logout from GUI
  }

}
