package cml.controller.messages

/**
  * Authentication response messages
  * @author Monica Gondolini
  */

// PER IL MOMENTO NON LI USIAMO, LASCIO PERCHè POTREMMO SEMPRE USARLI.

object AuthenticationResponse {

  sealed trait AuthenticationResponse
  sealed trait RegisterResponse extends AuthenticationResponse
  sealed trait LoginResponse extends AuthenticationResponse

  /**
    * Response for user registration succeeded
    * @param token is the token for user request
    */
  case class RegisterSuccess(token: String) extends RegisterResponse

  /**
    * Response for user registration failed
    * @param error failure message
    */
  case class RegisterFailure(error: String) extends RegisterResponse

  /**
    * Response for a user login succeeded
    * @param token is the token for user request
    */
  case class LoginSuccess(token: String) extends LoginResponse

  /**
    * Response for a user login failure
    * @param error failure message
    */
  case class LoginFailure(error: String) extends LoginResponse

}
