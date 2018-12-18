package cml.controller.messages

import cml.controller.fx.AuthenticationViewController

/**
  * Authentication request messages
 *
  * @author Monica Gondolini
  */

object AuthenticationRequest{

  sealed trait AuthenticationRequest

  /**
    * Request to register a new user in the system
    * @param username user's username
    * @param password user's password
    */
  case class Register(username: String, password: String) extends AuthenticationRequest

  /**
    * Request to login the user into the system
    * @param username user's username
    * @param password user's password
    */
  case class Login(username: String, password: String) extends AuthenticationRequest

  /**
    * Request to logout user in the system
    */
  case class Logout() extends AuthenticationRequest

  /**
    * Request for set controller in switch initial pane
    * @param controller controller identifier
    */
  case class SetController(controller: AuthenticationViewController) extends AuthenticationRequest
}
