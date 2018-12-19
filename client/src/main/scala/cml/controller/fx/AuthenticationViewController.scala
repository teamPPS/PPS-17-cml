package cml.controller.fx

import akka.actor.ActorSelection
import cml.controller.actor.utils.ActorUtils.ActorSystemInfo._
import cml.controller.actor.utils.ActorUtils.ActorPath.AuthenticationActorPath
import cml.controller.actor.utils.InputControl._
import cml.controller.messages.AuthenticationRequest.{Login, Register, SetController}
import cml.utils.ViewConfig._
import cml.view.ViewSwitch
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


  val authenticationActor: ActorSelection = system actorSelection AuthenticationActorPath

  def initialize(): Unit = {
    authenticationActor ! SetController(this)
  }

  @FXML def onRegister(): Unit = requestAuthentication(register, registrationUsernameField , registrationPasswordField)

  @FXML def onLogin(): Unit = requestAuthentication(login, loginUsernameField, loginPasswordField)

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