package cml.utils

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
}
