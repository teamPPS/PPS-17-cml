package cml.controller

import akka.actor.Actor
import cml.controller.messages.AuthenticationRequest.{Login, Logout, Register}
import cml.controller.messages.AuthenticationResponse.{LoginFailure, LoginSuccess, RegisterFailure, RegisterSuccess}
import cml.services.authentication.AuthenticationServiceVertx.AuthenticationServiceVertxImpl
import cml.utils.Configuration.AuthenticationMsg._
import javafx.application.Platform
import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.implicitConversions
import scala.util.{Failure, Success}

/**
  * Class that implements the actor which manages the authentication process
  * @author Monica Gondolini,Filippo Portolani
  *
  * @param controller controller of the authentication view
  */
class AuthenticationActor(controller: AuthenticationController) extends Actor{

  val authenticationVertx = AuthenticationServiceVertxImpl(controller.authenticationActor)

  var token: String = _

  override def receive: Receive = authenticationBehaviour

  /**
    * @return the authentication behaviour
    */
  private def authenticationBehaviour: Receive = {
    case Register(username, password) =>
      authenticationVertx.register(username, password)
    .onComplete{
        case Success(tokenResponse) =>
          successCase(tokenResponse)
          displayMsg(registerSuccess)
          println("Success this is server response with the token: " + tokenResponse)//debug
        case Failure(cause) => LoginFailure(cause.getMessage)
      }
    case Login(username, password) => authenticationVertx.login(username, password)
      .onComplete{
        case Success(tokenResponse) =>
          successCase(tokenResponse)
          println("Success this is server response with the token: " + tokenResponse)//debug
        case Failure(cause) => LoginFailure(cause.getMessage)
      }
    //case Logout(username) => clientVertx.logout(username) //rimuove utente dalla view del gioco (deve stare qui?)
  }

  /**
    * Displays text on the GUI through a label
    * @param m message to show
    */
  def displayMsg(m: String):Unit = {
    Platform.runLater(() => controller.formMsgLabel.setText(m))
  }

  def successCase(tokenValue: String): Unit = {
    token = tokenValue // token da appicciacare ad ogni richiesta
    println("TOKEN: " + token)
  }

  /**
    * Switches on and off GUI buttons
    * @param b boolean
    */
  def disableButtons(b: Boolean): Unit ={
    controller.registerButton.setDisable(b)
    controller.loginButton.setDisable(b)
  }
}
