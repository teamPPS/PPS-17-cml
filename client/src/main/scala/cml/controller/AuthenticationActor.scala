package cml.controller

import akka.actor.{Actor, ActorRef, Props}
import cml.controller.actor.utils.ViewMessage.ViewAuthenticationMessage._
import cml.controller.fx.AuthenticationViewController
import cml.controller.messages.AuthenticationRequest.{Login, Logout, Register}
import cml.controller.messages.AuthenticationResponse.{LoginFailure, RegisterFailure}
import cml.controller.messages.VillageRequest.CreateVillage
import cml.controller.messages.VillageResponse.{CreateVillageSuccess, DeleteVillageSuccess, VillageFailure}
import cml.services.authentication.AuthenticationServiceVertx.AuthenticationServiceVertxImpl
import cml.services.authentication.TokenStorage
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

  override def receive: Receive = authenticationBehaviour orElse villageBehaviour

  /**
    * @return the authentication behaviour
    */
  private def authenticationBehaviour: Receive = {
    case Register(username, password) => authenticationVertx.register(username, password)
      .onComplete {
        case Success(httpResponse) =>
          if(checkHttpResponse(httpResponse, loginSuccess, loginFailure))
            villageActor ! CreateVillage()
        case Failure(exception) =>
          RegisterFailure(exception.getMessage)
          displayMsg(registerFailure)
      }
    case Login(username, password) => authenticationVertx.login(username, password)
      .onComplete {
        case Success(httpResponse) =>
          if(checkHttpResponse(httpResponse, loginSuccess, loginFailure))
            loginSucceedOnGui()
        case Failure(exception) =>
          LoginFailure(exception.getMessage)
          displayMsg(loginFailure)
      }
    case Logout() => authenticationVertx.logout(TokenStorage.getUserJWTToken)

  }

  /**
    * @return behaviour when creating and entering a village
    */
  private def villageBehaviour: Receive = {
    case CreateVillageSuccess() =>
      println("Village create success")
      loginSucceedOnGui()
    case VillageFailure(m) => displayMsg(m)
    case DeleteVillageSuccess() =>
      println("delete village success - deleting user...")
      authenticationVertx.delete().onComplete{
        case Success(httpResponse) =>
          httpResponse match {
            case "Not a valid request" =>
              println("Failure to delete user")
            case _ => println("Deletion Done")
          }
        case Failure(exception) => println(exception)
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
    println("TOKEN: " + tokenValue)
    TokenStorage.setUserJWTToken(tokenValue)
  }

  def checkHttpResponse(str: String, successMessage: String, failureMessage: String): Boolean = str match { //technical debt?
    case "Not a valid request" =>
      displayMsg(failureMessage)
      false
    case _ =>
      successAuthenticationCase(str)
      displayMsg(successMessage)
      println("Success this is server response with the token: " + str)
      true
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
