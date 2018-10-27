package cml.controller


import akka.actor.{ActorRef, ActorSystem, Props}
import cml.controller.messages.AuthenticationRequest.{AuthenticationRequest, Login, Register}
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.{Button, TextField}

/**
  * Controller for the graphic user interface
  * @author Monica Gondolini
  */

class AuthenticationController{

  final var LOGIN: String = "login"
  final var REGISTER: String = "register"

  @FXML var usernameField: TextField = _
  @FXML var passwordField: TextField = _
  @FXML var registerBtn: Button = _
  @FXML var loginBtn: Button = _

  val system = ActorSystem("mySystem")
  val authenticationActor: ActorRef = system.actorOf(Props[AuthenticationActor], "authenticationActor")

  var username: String = _
  var password: String = _

  def initialize(): Unit = {
    loginBtn.setOnAction((_: ActionEvent) => requestAuthentication(LOGIN))
    registerBtn.setOnAction((_: ActionEvent) =>  requestAuthentication(REGISTER))
  }

  def requestAuthentication(msg: String): Unit ={
    username = usernameField.getText()
    password = passwordField.getText()

    if(msg == "login") authenticationActor ! Login(username, password)
    else if(msg == "register") authenticationActor ! Register(username, password)
}








}
