package cml.controller


import akka.actor.{ActorRef, ActorSystem, Props}
import cml.controller.messages.AuthenticationRequest.{Login, Register}
import javafx.fxml.FXML
import javafx.scene.control.{Button, TextField}

/**
  * Controller for the graphic user interface
  * @author Monica Gondolini
  */

class AuthenticationViewController{

  @FXML var usernameField: TextField = _
  @FXML var passwordField: TextField = _
  @FXML var registerBtn: Button = _
  @FXML var loginBtn: Button = _

  val system = ActorSystem("mySystem")
  val authenticationActor: ActorRef = system.actorOf(Props[AuthenticationActor], "authenticationActor")
  var username: String = ""
  var password: String = ""

  def initialize(): Unit = {

    loginBtn.setOnAction(_ => {
      username = usernameField.getText()
      password = passwordField.getText()
      authenticationActor ! Login(username, password)
    })

    registerBtn.setOnAction(_ => {
      username = usernameField.getText()
      password = passwordField.getText()
      authenticationActor ! Register(username, password)
    })
  }








}
