package cml.controller


import akka.actor.{ActorRef, ActorSystem, Props}
import cml.controller.messages.AuthenticationRequest.{Login, Register}
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.{Button, Label, PasswordField, TextField}
import cml.utils.Configuration.InputControl

/**
  * Controller for the graphic user interface
  * @author Monica Gondolini
  */

class AuthenticationController {

  final var LOGIN: String = "login"
  final var REGISTER: String = "register"

  @FXML var usernameField: TextField = _
  @FXML var passwordField: PasswordField = _
  @FXML var registerBtn: Button = _
  @FXML var loginBtn: Button = _
  @FXML var formMsgLabel: Label = _

  var username: String = _
  var password: String = _

  var system = ActorSystem("mySystem")
  var authenticationActor: ActorRef = system.actorOf(Props(new AuthenticationActor(this)), "authenticationActor")


  def initialize(): Unit = {
    loginBtn.setOnAction((_: ActionEvent) => requestAuthentication(LOGIN))
    registerBtn.setOnAction((_: ActionEvent) => requestAuthentication(REGISTER))
  }

  /**
    * Sends requests to the actor which manages the authentication,
    * @param msg defines which message to send to the authentication actor
    */
  def requestAuthentication(msg: String): Unit = {
    username = usernameField.getText()
    password = passwordField.getText()

    if(username.isEmpty || password.isEmpty){
      formMsgLabel.setText(InputControl.emptyFields)
    }

    if(username.matches(InputControl.userExp) && password.matches(InputControl.pswExp)){
      if (msg.equals(LOGIN)) authenticationActor ! Login(username, password)
      else if (msg.equals(REGISTER)) authenticationActor ! Register(username, password)
    }
  }

}