package cml.controller

import akka.actor.Actor
import cml.controller.messages.AuthenticationRequest.{Login, Logout, Register}
import cml.controller.messages.AuthenticationResponse.{LoginFailure, LoginSuccess, RegisterFailure, RegisterSuccess}
import javafx.application.Platform

/**
  * Class that implements the actor which manages the authentication process
  * @author Monica Gondolini, Filippo Portolani
  * @param controller controller of the authentication view
  */
class AuthenticationActor(controller: AuthenticationController) extends Actor{

  val clientVertx = ClientVertx(controller.authenticationActor)

  override def receive: Receive = authenticationBehaviour

  /**
    * @return the authentication behaviour
    */
  private def authenticationBehaviour: Receive = {
    case Register(username, password) => clientVertx.register(username, password)
    case Login(username, password) => clientVertx.login(username, password)
    case Logout(username) => clientVertx.logout(username) //rimuove utente dalla view del gioco (deve stare qui?)
    case RegisterSuccess(succ) => displayMsg(succ) //fare login subito dopo
    case RegisterFailure(err) => displayMsg(err)
    case LoginSuccess(succ) =>  displayMsg(succ) //cambio view con quella del villaggio
    case LoginFailure(err) => displayMsg(err)
  }

  /**
    * Displays text on the GUI through a label
    * @param m message to show
    */
  def displayMsg(m: String):Unit = {
    Platform.runLater(() => controller.formMsgLabel.setText(m))
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
