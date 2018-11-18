package cml.services.authentication

/**
  * This test class mach AuthenticationVerticle class is correct
  *
  * @author Chiara Volonnino
  */

import cml.core.TokenAuthentication
import cml.services.authentication.utils.AuthenticationUrl._
import cml.core.utils.NetworkConfiguration._
import io.netty.handler.codec.http.HttpHeaderNames
import io.netty.handler.codec.http.HttpResponseStatus._
import io.vertx.scala.core.Vertx
import io.vertx.scala.ext.web.client.WebClient

class AuthenticationVerticleTest extends AuthenticationServiceTest {

  private val vertx: Vertx = Vertx.vertx()
  private var client: WebClient = WebClient.create(vertx)

  private val username: String = "test"
  private val password: String = "test"
  private val invalidPassword: String = "test1"
  private var token: String = _
  private val invalidToken = "ppp"

  private def base64Test(username: String, password: String): String = {
    val handler = TokenAuthentication.base64Authentication(username, password)
    handler.get
  }

  private def tokenTest(token: String): String = {
    val handler = TokenAuthentication.authenticationToken(token)
    handler.get
  }

  test("Registration test"){
    println("Response bad request because handler is empty")
    client.post(AuthenticationServicePort, ServiceHostForRequest, RegisterApi)
      .sendFuture
      .map(response => assert(response.statusCode().toString equals BAD_REQUEST.code().toString))

    println("Response create because handler is corrected create")
    client.post(AuthenticationServicePort, ServiceHostForRequest, RegisterApi)
      .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), base64Test(username, password))
      .sendFuture
      .map(response =>
        assert(response.statusCode().toString equals CREATED.code().toString))

    println("Response bad request because handler is invalid")
    client.post(AuthenticationServicePort, ServiceHostForRequest, RegisterApi)
      .putHeader("", "")
      .sendFuture
      .map({ response =>
        assert(response.statusCode().toString equals BAD_REQUEST.code().toString)
      })
  }

  test("Login test") {
    println("Response bad request because handler is empty")
    client.put(AuthenticationServicePort, ServiceHostForRequest, LoginApi)
      .sendFuture
      .map(response => assert(response.statusCode().toString equals BAD_REQUEST.code().toString))

    println("Response ok because handler is corrected create")
    client.put(AuthenticationServicePort, ServiceHostForRequest, LoginApi)
      .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), base64Test(username, password))
      .sendFuture
      .map(response =>assert(response.statusCode().toString equals OK.code().toString))

    /* DA SCOMMENTARE QUANDO DB RISPONDE BENE
    println("Response unauthorized because handler is incorrect")
    client.put(AuthenticationServicePort, ServiceHostForRequest, LoginApi)
      .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), base64Test(username, invalidPassword))
      .sendFuture
      .map(response => assert(response.statusCode().toString equals UNAUTHORIZED.code().toString))*/
  }

  test("Logout test") {
    println("Response bad request because handler is empty")
    client.delete(AuthenticationServicePort, ServiceHost, LogoutApi)
      .sendFuture
      .map(response => assert(response.statusCode().toString equals BAD_REQUEST.code().toString))

    /* DA VALUTARE, NON CAPISCO CHE PROBLEMA CI SIA
    println("Response ok because token success delete")
    val response = client.post(AuthenticationServicePort, ServiceHost, LoginApi)
      .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), base64Test(username, password))
      .sendFuture

    token = response.toString

    client.delete(AuthenticationServicePort, ServiceHost, LogoutApi)
      .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), tokenTest(token))
      .sendFuture
      .map(response => {
        println("Response: "  + response.statusCode().toString)
        println(token)
        assert(response.statusCode().toString equals OK.code().toString)})*/

    println("Response bad request because token is invalid")
    client.delete(AuthenticationServicePort, ServiceHost, LogoutApi)
      .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), tokenTest(invalidToken))
      .sendFuture
      .map(response => assert(response.statusCode().toString equals BAD_REQUEST.code().toString))
  }

  test("Validation token test") {
    println("Response bad request because handler is empty")
    client.post(AuthenticationServicePort, ServiceHostForRequest, RegisterApi)
      .sendFuture
      .map(response => assert(response.statusCode().toString equals BAD_REQUEST.code().toString))
  }
}
