package cml.services.authentication

import cml.core.utils.JWTAuthentication
import org.scalatest.FunSuite

/**
  * This test class matches if TokenStorage class is correct
  * @author Monica Gondolini
  */

class TokenStorageTest extends FunSuite{

  val username = "john"
  val token: Option[String] = JWTAuthentication.encodeUsernameToken(username)

  test("Set user token"){
    TokenStorage.setUserJWTToken(token.get)
    assert(TokenStorage.getUserJWTToken.nonEmpty)
  }

}
