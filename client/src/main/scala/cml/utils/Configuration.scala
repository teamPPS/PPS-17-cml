package cml.utils
/**
  * Configuration constants of the application
  * @author Monica Gondolini
  */
object Configuration {

  object Connection{
    final val port: Int = 8080
    final val host: String = "myserver.mycompany.com"
    final val requestUri: String = "/some-uri"
  }

  object AuthenticationMsg{
    final val loginSuccess: String  = "Login avvenuto con successo!"
    final val loginFailure: String  = "Login rifiutato."
    final val registerSuccess: String  = "Registrazione completata con successo!"
    final val registerFailure: String  = "C'è stato un problema, la registrazione non è stata effettuata."
  }

  object InputControl{
    final val emptyFields: String = "Some fields are empty"
    final val userExp: String = "^[A-Za-z0-9]+$"
    final val pswExp: String = "^[A-Za-z0-9]+$"
  }
}
