package cml.controller.messages

object AuthenticationResponse {

  sealed trait AuthenticationResponse
  sealed trait LoginResponse extends AuthenticationResponse
  sealed trait RegisterResponse extends AuthenticationResponse

  case class LoginSuccess(username: String, password: String) extends LoginResponse

  case class LoginFailure(username: String, password: String) extends LoginResponse

  case class RegisterSuccess(username: String, password: String) extends RegisterResponse

  case class RegisterFailure(username: String, password: String) extends RegisterResponse

}
