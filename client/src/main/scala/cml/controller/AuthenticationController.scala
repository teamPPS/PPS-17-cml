package cml.controller


import akka.actor.{ActorRef, ActorSystem, Props}
import cml.controller.messages.AuthenticationRequest.{Login, Register}
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.{Button, Label, PasswordField, TextField}
import cml.utils.Configuration.{ControllerMsg, InputControl, VillageWindow}
import cml.view.ViewSwitch

/**
  * Controller for the graphic user interface
  * @author Monica Gondolini,Filippo Portolani
  */

class AuthenticationController {

  @FXML var registrationUsernameField: TextField = _
  @FXML var registrationPasswordField: PasswordField = _
  @FXML var loginUsernameField: TextField = _
  @FXML var loginPasswordField: PasswordField = _
  @FXML var registerButton: Button = _
  @FXML var loginButton: Button = _
  @FXML var formMsgLabel: Label = _

  var system = ActorSystem("mySystem")
  var authenticationActor: ActorRef = system actorOf(Props(new AuthenticationActor(this)), "authenticationActor")


  def initialize(): Unit = {
    registerButton setOnAction((_: ActionEvent) => requestAuthentication(ControllerMsg register, registrationUsernameField , registrationPasswordField))
    loginButton setOnAction((_: ActionEvent) => requestAuthentication(ControllerMsg login, loginUsernameField, loginPasswordField))
  }

  /**
    * Sends requests to the actor which manages the authentication
    * @param msg defines which message to send to the authentication actor
    */
  def requestAuthentication(msg: String, usernameField: TextField, passwordField: PasswordField): Unit ={
      val username = usernameField getText()
      val password = passwordField getText()

      if(username.isEmpty ||password.isEmpty) {
        formMsgLabel setText (InputControl emptyFields)
      }

      if(username.matches(InputControl userExp) && password.matches(InputControl pswExp)) {
        if(msg.equals(ControllerMsg register)) authenticationActor ! Register(username, password)
        else if(msg.equals(ControllerMsg login)) authenticationActor ! Login(username, password)
      }

      ViewSwitch.activate(VillageWindow.path, loginButton.getScene) //technical debt, have to change when login succesfull
  }

}