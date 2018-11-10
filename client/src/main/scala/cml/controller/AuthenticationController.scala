package cml.controller


import akka.actor.{ActorRef, ActorSystem, Props}
import cml.controller.messages.AuthenticationRequest.{Login, Register}
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.{Button, Label, PasswordField, TextField}
import cml.utils.Configuration.{ControllerMsg, InputControl}

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

  var registrationUsername: String = _
  var registrationPassword: String = _
  var loginUsername: String = _
  var loginPassword: String = _

  var system = ActorSystem("mySystem")
  var authenticationActor: ActorRef = system actorOf(Props(new AuthenticationActor(this)), "authenticationActor")


  def initialize(): Unit = {
    registerButton setOnAction((_: ActionEvent) => requestRegistration(ControllerMsg register))
    loginButton setOnAction((_: ActionEvent) => requestLogin(ControllerMsg login))
  }

  /**
    * Sends requests to the actor which manages the authentication
    * @param msg defines which message to send to the authentication actor
    */
  def requestRegistration(msg: String): Unit = {
    registrationUsername = registrationUsernameField getText()
    registrationPassword = registrationPasswordField getText()

    if(registrationUsername.isEmpty || registrationPassword.isEmpty){
      formMsgLabel setText(InputControl emptyFields)
    }

    if(registrationUsername.matches(InputControl userExp) && registrationPassword.matches(InputControl pswExp)){
      authenticationActor ! Register(registrationUsername, registrationPassword)
    }
    registrationUsernameField setText("")
    registrationPasswordField setText("")
  }

  def requestLogin(msg: String): Unit = {
    loginUsername = loginUsernameField getText()
    loginPassword = loginPasswordField getText()

    if(loginUsername.isEmpty || loginPassword.isEmpty){
      formMsgLabel setText(InputControl emptyFields)
    }
    authenticationActor ! Login(loginUsername,loginPassword)

    loginUsernameField setText("")
    loginPasswordField setText("")
  }

}