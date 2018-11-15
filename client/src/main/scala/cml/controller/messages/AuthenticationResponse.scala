package cml.controller.messages

/**
  * Authentication response messages
  * @author Monica Gondolini,Filippo Portolani
  */

object AuthenticationResponse {

  sealed trait AuthenticationResponse
  sealed trait RegisterResponse extends AuthenticationResponse
  sealed trait LoginResponse extends AuthenticationResponse

  /**
    * Response for user registration succeeded
    * @param succ success message
    * @param token is the token for user request
    */
  case class RegisterSuccess(succ: String, token: String) extends RegisterResponse

  /**
    * Response for user registration failed
    * @param err failure message
    */
  case class RegisterFailure(err: String) extends RegisterResponse

  /**
    * Response for a user login succeeded
    * @param succ success message
    * @param token is the token for user request
    */
  case class LoginSuccess(succ: String, token: String) extends LoginResponse

  /**
    * Response for a user login failure
    * @param err failure message
    */
  case class LoginFailure(err: String) extends LoginResponse

}
