package cml.controller

import akka.actor.Actor
import cml.controller.messages.AuthenticationRequest.{Login, Logout, Register}
import io.vertx.scala.core.Vertx
import io.vertx.scala.ext.web.client.WebClient
import javafx.application.Platform

import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global


class AuthenticationActor(controller: AuthenticationController) extends Actor{


  var vertx: Vertx = Vertx.vertx()
  var client: WebClient = WebClient.create(vertx)


  override def receive: Receive = {
    case Login(username, password) => {
      println(s"sending login request from username:$username with password:$password")
      disableButtons(true)
      client.post(8080, "myserver.mycompany.com", "/some-uri")
        .sendJsonObjectFuture(new io.vertx.core.json.JsonObject().put("username", username).put("password", password))
        .onComplete{
          case Success(result) => {
            println(result)
            Platform.runLater(() => controller.formMsgLabel.setText("Login avvenuto con successo!"))
          }
          case Failure(cause) =>{
            println("Failure")
            Platform.runLater(() => controller.formMsgLabel.setText("Login rifiutato"))
            disableButtons(false)
          }
        }
    }
    case Register(username, password) => {
      println(s"sending registration request from username:$username with password:$password")
      disableButtons(true)
      client.post(8080, "myserver.mycompany.com", "/some-uri")
        .sendJsonObjectFuture(new io.vertx.core.json.JsonObject().put("username", username).put("password", password))
        .onComplete{
          case Success(result) => {
            println(result)
            Platform.runLater(() => controller.formMsgLabel.setText("Registrazione completata con successo!"))
          }
          case Failure(cause) => {
            println("Failure")
            Platform.runLater(() => controller.formMsgLabel.setText("C'è stato un problema, la registrazione non è stata effettuata."))
            disableButtons(false)
          }
        }
    }

    case Logout(username) => ???

  }

  def disableButtons(b: Boolean): Unit ={
    controller.registerBtn.setDisable(b)
    controller.loginBtn.setDisable(b)
  }
}
