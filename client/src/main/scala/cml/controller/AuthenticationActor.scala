package cml.controller

import akka.actor.Actor
import cml.controller.messages.AuthenticationRequest.{Login, Logout, Register}

/**
  * Class that implements the actor which manages the authentication process
  * @author Monica Gondolini
  * @param controller controller of the authentication view
  */
class AuthenticationActor(controller: AuthenticationController) extends Actor{

  override def receive: Receive = authenticationBehaviour

  /**
    * @return the authentication behaviour
    */
  private def authenticationBehaviour: Receive = {

    case Login(username, password) => {
      println(s"sending login request from username:$username with password:$password")
      disableButtons(true)
      val clientVertx = ClientVertx(controller).login(username, password)
    }

    case Register(username, password) => {
      println(s"sending registration request from username:$username with password:$password")
      disableButtons(true)
      val clientVertx = ClientVertx(controller).register(username, password)
    }

    case Logout(username) => ??? //rimuove utente dalla view del gioco (deve stare qui?)



  }


  /**
    * Switches on and off GUI buttons
    * @param b boolean
    */
  def disableButtons(b: Boolean): Unit ={
    controller.registerBtn.setDisable(b)
    controller.loginBtn.setDisable(b)
  }


}
