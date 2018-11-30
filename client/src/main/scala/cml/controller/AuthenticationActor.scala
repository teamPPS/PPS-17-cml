package cml.controller

import akka.actor.{Actor, ActorRef, Props}
import cml.controller.actor.utils.ViewMessage.ViewAuthenticationMessage._
import cml.controller.fx.AuthenticationViewController
import cml.controller.messages.AuthenticationRequest.{Login, Logout, Register}
import cml.controller.messages.AuthenticationResponse.{LoginFailure, RegisterFailure}
import cml.controller.messages.VillageRequest
import cml.controller.messages.VillageRequest.{CreateVillage, EnterVillage}
import cml.controller.messages.VillageResponse.{CreateVillageSuccess, EnterVillageSuccess, VillageFailure}
import cml.services.authentication.AuthenticationServiceVertx.AuthenticationServiceVertxImpl
import javafx.application.Platform

import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.implicitConversions
import scala.util.{Failure, Success}

/**
  * Class that implements the actor which manages the authentication process
  *
  * @author Monica Gondolini
  * @author (edited by) Chiara Volonnino, Filippo Portolani
  * @param controller controller of the authentication view
  */
class AuthenticationActor(controller: AuthenticationViewController) extends Actor {

  val authenticationVertx = AuthenticationServiceVertxImpl()
  val villageActor: ActorRef = context.system.actorOf(Props(new VillageActor()), "VillageActor")

  var token: String = _

  override def receive: Receive = authenticationBehaviour orElse villageBehaviour

  /**
    * @return the authentication behaviour
    */
  private def authenticationBehaviour: Receive = {
    case Register(username, password) => authenticationVertx.register(username, password)
      .onComplete {
        case Success(httpResponse) =>
          checkResponse(httpResponse, registerSuccess, registerFailure)
          villageActor ! CreateVillage()
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
    * @return behaviour when creating and entering a village
    */
  private def villageBehaviour: Receive = {
    case CreateVillageSuccess() =>
      println("Village create success")
      loginSucceedOnGui()
    case VillageFailure(m) => displayMsg(m)
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
      loginSucceedOnGui() //questo si fa quando viene ricevuta la risposta di entrata al villaggio
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
