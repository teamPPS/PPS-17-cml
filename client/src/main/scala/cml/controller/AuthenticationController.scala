package cml.controller


import akka.actor.{ActorRef, ActorSystem, Props}
import cml.controller.messages.AuthenticationRequest.{Login, Register}
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.{Button, Label, TextField}

/**
  * Controller for the graphic user interface
  * @author Monica Gondolini
  */

class AuthenticationController {

  final var LOGIN: String = "login"
  final var REGISTER: String = "register"

  @FXML var usernameField: TextField = _
  @FXML var passwordField: TextField = _
  @FXML var registerBtn: Button = _
  @FXML var loginBtn: Button = _
  @FXML var formMsgLabel: Label = _

  val system = ActorSystem("mySystem")
  val authenticationActor: ActorRef = system.actorOf(Props(new AuthenticationActor(this)), "authenticationActor")

  var username: String = _
  var password: String = _

  def initialize(): Unit = {
    loginBtn.setOnAction((_: ActionEvent) => requestAuthentication(LOGIN))
    registerBtn.setOnAction((_: ActionEvent) => requestAuthentication(REGISTER))
  }

  def requestAuthentication(msg: String): Unit = {
    username = usernameField.getText()
    password = passwordField.getText()

    if (msg == LOGIN) authenticationActor ! Login(username, password)
    else if (msg == REGISTER) authenticationActor ! Register(username, password)
  }

}