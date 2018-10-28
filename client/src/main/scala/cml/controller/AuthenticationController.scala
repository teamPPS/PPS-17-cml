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

  var username: String = _
  var password: String = _

  var system = ActorSystem("mySystem")
  var authenticationActor: ActorRef = system.actorOf(Props(new AuthenticationActor(this)), "authenticationActor")

  def initialize(): Unit = {
    loginBtn.setOnAction((_: ActionEvent) => requestAuthentication(LOGIN))
    registerBtn.setOnAction((_: ActionEvent) => requestAuthentication(REGISTER))
  }

  /**
    * Sends requests to the actor which manages the authentication
    * @param msg defines which message to send to the authentication actor
    */
  def requestAuthentication(msg: String): Unit = {
    username = usernameField.getText()
    password = passwordField.getText()

    val usrExp: String = "([A-Za-z0-9])"
    val pswExp: String = "([A-Za-z0-9])"
      //"^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"  //Minimum eight characters, at least one letter and one number:

    if(username.isEmpty || password.isEmpty){
      formMsgLabel.setText("Some fields are empty")
    }

//    if(username.matches(usrExp) && password.matches(pswExp)){
      if (msg == LOGIN) authenticationActor ! Login(username, password)
      else if (msg == REGISTER) authenticationActor ! Register(username, password)
//    }
  }

}