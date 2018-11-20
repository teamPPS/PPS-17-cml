package cml.controller

import akka.actor.Actor
import cml.controller.actor.utils.ViewAuthenticationMessage._
import cml.controller.fx.AuthenticationViewController
import cml.controller.messages.AuthenticationRequest.{Login, Register}
import cml.controller.messages.AuthenticationResponse.{LoginFailure, RegisterFailure}
import cml.services.authentication.AuthenticationServiceVertx.AuthenticationServiceVertxImpl
import javafx.application.Platform

import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.implicitConversions
import scala.util.{Failure, Success}

/**
  * Class that implements the actor which manages the authentication process
  *
  * @author Monica Gondolini,Filippo Portolani
  * @author (modified by) Chiara Volonnino
  * @param controller controller of the authentication view
  */
class AuthenticationActor(controller: AuthenticationViewController) extends Actor {

  val authenticationVertx = AuthenticationServiceVertxImpl(controller.authenticationActor)

  var token: String = _

  override def receive: Receive = authenticationBehaviour

  /**
    * @return the authentication behaviour
    */
  private def authenticationBehaviour: Receive = {
    case Register(username, password) => authenticationVertx.register(username, password)
      .onComplete {
        case Success(httpResponse) =>
          checkResponse(httpResponse, registerSuccess, registerFailure)
        case Failure(exception) =>
          RegisterFailure(exception.getMessage)
          displayMsg(registerFailure)
      }
    case Login(username, password) => authenticationVertx.login(username, password)
      .onComplete {
        case Success(httpResponse) =>
          checkResponse(httpResponse, loginSuccess, loginFailure)
        case Failure(exception) =>
          LoginFailure(exception.getMessage)
          displayMsg(loginFailure)
      }
  }

  /**
    * Displays text on the GUI through a label
    *
    * @param m message to show
    */
  def displayMsg(m: String): Unit = Platform.runLater(() => controller.formMsgLabel.setText(m))

  /**
    * Open Village View after login successful
    */
  def loginSucceedOnGui(): Unit = Platform.runLater(() => controller.openVillageView())

  def successAuthenticationCase(tokenValue: String): Unit = {
    token = tokenValue
    println("TOKEN: " + token)
  }

  def checkResponse(str: String, successMessage: String, failureMessage: String): Unit = str match { //technical debt?
    case "Not a valid request" =>
      displayMsg(failureMessage)
    case _ =>
      successAuthenticationCase(str)
      displayMsg(successMessage)
      loginSucceedOnGui()
      println("Success this is server response with the token: " + str)
  }

  /**
    * Switches on and off GUI buttons
    *
    * @param b boolean
    */
  def disableButtons(b: Boolean): Unit = {
    controller.registerButton.setDisable(b)
    controller.loginButton.setDisable(b)
  }
}
