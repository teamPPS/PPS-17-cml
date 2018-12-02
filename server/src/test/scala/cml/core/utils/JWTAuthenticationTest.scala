package cml.core.utils

import org.scalatest.FunSuite

/**
  * This is a class test for JWTAuthentication
  * @author Filippo Portolani
  */

class JWTAuthenticationTest extends FunSuite {

  val username1 : String = "Filippo"
  val username2 : String = "Luca"
  val token1 : Option[String] = JWTAuthentication.encodeUsernameToken(username1)
  val token2 : Option[String] = JWTAuthentication.encodeUsernameToken(username1)

  test("test to verify that an encoded token is different from another one"){

    assert(JWTAuthentication.encodeUsernameToken(username1) != JWTAuthentication.encodeUsernameToken(username2))
  }

  test("test to verify that an encoded token is equal to another one"){

    assert(token1.equals(token1))
  }



}
