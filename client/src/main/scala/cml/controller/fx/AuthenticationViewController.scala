package cml.controller.fx

import akka.actor.{ActorRef, Props}
import cml.controller.AuthenticationActor
import cml.controller.messages.AuthenticationRequest.{Login, Register}
import cml.controller.actor.utils.AppActorSystem.system
import cml.view.ViewSwitch
import cml.controller.actor.utils.InputControl._
import cml.utils.ViewConfig._
import javafx.fxml.FXML
import javafx.scene.control.{Button, Label, PasswordField, TextField}

/**
  * Controller for the graphic user interface
  * @author Monica Gondolini, Filippo Portolani
  */

class AuthenticationViewController {

  @FXML var registrationUsernameField: TextField = _
  @FXML var registrationPasswordField: PasswordField = _
  @FXML var loginUsernameField: TextField = _
  @FXML var loginPasswordField: PasswordField = _
  @FXML var registerButton: Button = _
  @FXML var loginButton: Button = _
  @FXML var formMsgLabel: Label = _


  var authenticationActor: ActorRef = system actorOf(Props(new AuthenticationActor(this)), "AuthenticationActor")


  def initialize(): Unit = {
    registerButton setOnAction(_ => requestAuthentication(register, registrationUsernameField , registrationPasswordField))
    loginButton setOnAction(_ => requestAuthentication(login, loginUsernameField, loginPasswordField))
  }

  /**
    * Sends requests to the actor which manages the authentication
    * @param msg defines which message to send to the authentication actor
    */
  def requestAuthentication(msg: String, usernameField: TextField, passwordField: PasswordField): Unit ={
    val username = usernameField getText()
    val password = passwordField getText()

    if(username.isEmpty || password.isEmpty) {
      formMsgLabel setText emptyFields
    }

    if(username.matches(userExp) && password.matches(pswExp)){
      if(msg.equals(register)) authenticationActor ! Register(username, password)
      else if(msg.equals(login)) authenticationActor ! Login(username, password)
    }
  }

  def openVillageView(): Unit = {
    ViewSwitch.activate(VillageWindow.path, loginButton.getScene)
  }
}