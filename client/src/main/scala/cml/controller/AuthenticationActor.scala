package cml.controller

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import cml.controller.actor.utils.ViewMessage.ViewAuthenticationMessage.{loginFailure, loginSuccess, registerFailure, registerSuccess}
import cml.controller.fx.AuthenticationViewController
import cml.controller.messages.AuthenticationRequest.{Login, Logout, Register, SetController}
import cml.controller.messages.AuthenticationResponse.{LoginFailure, RegisterFailure}
import cml.controller.messages.VillageRequest.CreateVillage
import cml.controller.messages.VillageResponse.{CreateVillageSuccess, VillageFailure}
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
class AuthenticationActor(controller: AuthenticationViewController) extends Actor with ActorLogging{

  val authenticationVertx = AuthenticationServiceVertxImpl()
  val villageActor: ActorRef = context.system.actorOf(Props[VillageActor], "VillageActor")
  var authController: AuthenticationViewController = controller

  override def receive: Receive = authenticationBehaviour orElse villageBehaviour

  private def authenticationBehaviour: Receive = {
    case Register(username, password) => authenticationVertx.register(username, password)
      .onComplete {
        case Success(httpResponse) =>
          if(checkHttpResponse(httpResponse, registerSuccess, registerFailure))
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
    case SetController(controller: AuthenticationViewController) =>  authController = controller
  }

  private def villageBehaviour: Receive = {
    case CreateVillageSuccess() =>
      log.info("Village created with success")
      loginSucceedOnGui()
    case VillageFailure(m) => displayMsg(m)
  }

  def displayMsg(m: String): Unit = Platform.runLater(() => authController.formMsgLabel.setText(m))

  def loginSucceedOnGui(): Unit = Platform.runLater(() => authController.openVillageView())

  def successAuthenticationCase(tokenValue: String): Unit = {
    log.info("TOKEN: " + tokenValue)
    TokenStorage.setUserJWTToken(tokenValue)
  }

  def checkHttpResponse(str: String, successMessage: String, failureMessage: String): Boolean = str match {
    case "Not a valid request" =>
      displayMsg(failureMessage)
      false
    case _ =>
      successAuthenticationCase(str)
      displayMsg(successMessage)
      log.info("Success this is server response with the token: " + str)
      true
  }
  
  def disableButtons(b: Boolean): Unit = {
    authController.registerButton.setDisable(b)
    authController.loginButton.setDisable(b)
  }
}
