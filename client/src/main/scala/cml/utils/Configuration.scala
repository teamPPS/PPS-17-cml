package cml.utils

object Configuration {

  object Connection{
    val port: Int = 8080
    val host: String = "127.0.0.1"  //"localhost"
    val requestUri: String ="/api/authentication/register" // "/uri"
    val login: String = "/api/authentication/login"
  }

  object AuthenticationMsg{
    val loginSuccess: String  = "Login succeeded!"
    val loginFailure: String  = "Login denied."
    val registerSuccess: String  = "Registration completed!"
    val registerFailure: String  = "Error, registration not completed."
  }

  object InputControl{
    val emptyFields: String = "Some fields are empty."
    val userExp: String = "^[A-Za-z0-9]+$"
    val pswExp: String = "^[A-Za-z0-9]+$"
  }

  object ControllerMsg{
    val login: String = "login"
    val register: String = "register"
  }

}
