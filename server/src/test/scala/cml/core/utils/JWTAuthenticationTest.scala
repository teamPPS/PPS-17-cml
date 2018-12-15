package cml.core.utils

import org.scalatest.FunSuite

/**
  * This is a class test for JWTAuthentication
  * @author Filippo Portolani
  */

class JWTAuthenticationTest extends FunSuite {

  val username1 : String = "Filippo"
  val username2 : String = "Luca"

  test("test to verify that an encoded token is different from another one"){
    assert(JWTAuthentication.encodeUsernameToken(username1) != JWTAuthentication.encodeUsernameToken(username2))
  }

  test("test to verify that an encoded token is equal to another one"){
    assert(JWTAuthentication.encodeUsernameToken(username1).get.equals(JWTAuthentication.encodeUsernameToken(username1).get))
  }

  test("test to verify that decode work right") {
    val encodedUser = JWTAuthentication.encodeUsernameToken(username1)
    assert(username1.equals(JWTAuthentication.decodeUsernameToken(encodedUser.get).get))
  }

  test("test validation of a token") {
    val encodedUser = JWTAuthentication.encodeUsernameToken(username1)
    assert(JWTAuthentication.validateToken(encodedUser.get))
  }

}
