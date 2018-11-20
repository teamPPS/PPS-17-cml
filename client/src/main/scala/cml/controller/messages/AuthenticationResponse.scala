package cml.controller.messages

/**
  * Authentication response messages
  * @author Monica Gondolini,Filippo Portolani
  */

// PER IL MOMENTO NON LI USIAMO, LASCIO PERCHè POTREMMO SEMPRE USARLI.

object AuthenticationResponse {

  sealed trait AuthenticationResponse
  sealed trait RegisterResponse extends AuthenticationResponse
  sealed trait LoginResponse extends AuthenticationResponse

  /**
    * Response for user registration succeeded
    * @param succ success message
    * @param token is the token for user request
    */
  case class RegisterSuccess(token: String) extends RegisterResponse

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
  case class LoginSuccess(token: String) extends LoginResponse

  /**
    * Response for a user login failure
    * @param err failure message
    */
  case class LoginFailure(err: String) extends LoginResponse

}
