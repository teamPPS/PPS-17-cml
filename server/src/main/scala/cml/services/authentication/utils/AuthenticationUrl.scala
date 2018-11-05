package cml.services.authentication.utils

/**
  * This class define Authentications API path for utils another class
  *
  * @author Chiara Volonnino
  */

object AuthenticationUrl {


  private val GENERAL_PATH = "/api/authentication"

  val REGISTER_API = s"$GENERAL_PATH/register"
  val LOGIN_API = s"$GENERAL_PATH/login"
  val LOGOUT_API = s"$GENERAL_PATH/logout"
  val DELETE_API = s"$GENERAL_PATH/delete"
  val VALIDATION_TOKEN_API = s"$GENERAL_PATH/validationToken"
}
