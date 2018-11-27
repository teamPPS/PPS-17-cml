package cml.controller.messages

/**
  * Authentication request messages
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
    * Request to log the user into the system
    * @param username user's username
    * @param password user's password
    */
  case class Login(username: String, password: String) extends AuthenticationRequest

  case class Logout() extends AuthenticationRequest
}
