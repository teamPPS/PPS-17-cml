package cml.services.authentication.utils

/**
  * This class define Authentications API path for utils another class
  *
  * @author Chiara Volonnino
  */

object AuthenticationUrl {
  
    private val GeneralPath = "/api/authentication"

    val RegisterApi = s"$GeneralPath/register"
    val LoginApi = s"$GeneralPath/login"
    val LogoutApi = s"$GeneralPath/logout"
    val DeleteApi = s"$GeneralPath/delete"
    val ValidationTokenApi = s"$GeneralPath/validationToken"
}