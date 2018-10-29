package cml.controller

import akka.actor.Actor
import cml.controller.messages.AuthenticationRequest.{Login, Logout, Register}
import io.vertx.lang.scala.json.JsonObject
import io.vertx.scala.core.Vertx
import io.vertx.scala.ext.web.client.WebClient
import javafx.application.Platform

import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global
import cml.utils.Configuration.{Connection, AuthenticationMsg}


/**
  * Class that implements the actor which manages the authentication process
  * @author Monica Gondolini
  * @param controller controller of the authentication view
  */
class AuthenticationActor(controller: AuthenticationController) extends Actor{

  final var MILLIS = 1000

  var vertx: Vertx = Vertx.vertx()
  var client: WebClient = WebClient.create(vertx)

  override def receive: Receive = authenticationBehaviour

  /**
    * @return the authentication behaviour
    */
  private def authenticationBehaviour: Receive = {

    case Login(username, password) =>
      println(s"sending login request from username:$username with password:$password")
      disableButtons(true)

      client.post(Connection.port, Connection.host, Connection.requestUri)
        .sendJsonObjectFuture(new JsonObject().put("username", username).put("password", password))
        .onComplete{
          case Success(result) =>
            println(result)
            Platform.runLater(() => controller.formMsgLabel.setText(AuthenticationMsg.loginSuccess))
            //sostituire la view con quella del villaggio mandando un messaggio all'attore del villaggio (?)
          case Failure(cause) =>
            println("Failure")
            Platform.runLater(() => controller.formMsgLabel.setText(AuthenticationMsg.loginFailure))
            disableButtons(false)
        }

    case Register(username, password) =>
      println(s"sending registration request from username:$username with password:$password")
      disableButtons(true)

      client.post(Connection.port, Connection.host, Connection.requestUri)
        .sendJsonObjectFuture(new JsonObject().put("username", username).put("password", password))
        .onComplete{
          case Success(result) =>
            println(result)
            Platform.runLater(() => controller.formMsgLabel.setText(AuthenticationMsg.registerSuccess))
          case Failure(cause) =>
            println("Failure")
            Platform.runLater(() => controller.formMsgLabel.setText(AuthenticationMsg.registerFailure))
            disableButtons(false)
        }

    case Logout(username) => ??? //rimuove utente dalla view del gioco (deve stare qui?)

  }


  /**
    * Switches on and off GUI buttons
    * @param b boolean
    */
  def disableButtons(b: Boolean): Unit ={
    controller.registerBtn.setDisable(b)
    controller.loginBtn.setDisable(b)
  }


}
