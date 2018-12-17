package cml.services.authentication

/**
  * This test class matches if AuthenticationVerticle class is correct
  *
  * @author Chiara Volonnino, ecavina
  */

import cml.core.TokenAuthentication
import cml.services.authentication.utils.AuthenticationUrl._
import cml.core.utils.NetworkConfiguration._
import io.netty.handler.codec.http.HttpHeaderNames
import io.netty.handler.codec.http.HttpResponseStatus._
import io.vertx.scala.core.Vertx
import io.vertx.scala.ext.web.client.WebClient

import scala.language.implicitConversions

class AuthenticationVerticleTest extends AuthenticationServiceTest {

  private val client: WebClient = WebClient.create(vertx)

  private val inputTest: String = "test"
  private val invalidPassword: String = "test1"
  private val inputForTokenReturn: String = "testToken"
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
    client.post(ServicePort, ServiceHostForRequest, RegisterApi)
      .sendFuture
      .map(response => assert(response.statusCode().toString equals BAD_REQUEST.code().toString))

    println("Response create because handler is correctly created")
    client.post(ServicePort, ServiceHostForRequest, RegisterApi)
      .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), base64Test(inputTest, inputTest))
      .sendFuture
      .map(response =>
        assert(response.statusCode().toString equals CREATED.code().toString))

    println("Response bad request because handler is invalid")
    client.post(ServicePort, ServiceHostForRequest, RegisterApi)
      .putHeader("", "")
      .sendFuture
      .map({ response =>
        assert(response.statusCode().toString equals BAD_REQUEST.code().toString)
      })
  }

  test("Login test") {
    println("Response bad request because handler is empty")
    client.put(ServicePort, ServiceHostForRequest, LoginApi)
      .sendFuture
      .map(response => assert(response.statusCode().toString equals BAD_REQUEST.code().toString))

    println("Response ok because handler is correctly created")
    client.put(ServicePort, ServiceHostForRequest, LoginApi)
      .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), base64Test(inputTest, inputTest))
      .sendFuture
      .map(response => {
        println("res " +response )
        assert(response.statusCode().toString equals OK.code().toString)
      })

    println("Response unauthorized because handler is incorrect")
    client.put(ServicePort, ServiceHostForRequest, LoginApi)
      .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), base64Test(inputTest, invalidPassword))
      .sendFuture
      .map(response => assert(response.statusCode().toString equals UNAUTHORIZED.code().toString))
  }

  test("Logout test") {
    println("Response bad request because handler is empty")
    client.delete(ServicePort, ServiceHostForRequest, LogoutApi)
      .sendFuture
      .map(response => assert(response.statusCode().toString equals BAD_REQUEST.code().toString))


    println("Response ok because token success delete")
    client.delete(ServicePort, ServiceHostForRequest, LogoutApi)
      .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), base64Test(inputForTokenReturn, inputForTokenReturn))
      .sendFuture
      .map(response => assert(response.statusCode().equals(BAD_REQUEST.code())))

    println("Response bad request because token is invalid")
    client.delete(ServicePort, ServiceHostForRequest, LogoutApi)
      .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), tokenTest(invalidToken))
      .sendFuture
      .map(response => assert(response.statusCode().toString equals UNAUTHORIZED.code().toString))

  }

  test("Validation token test") {
    println("Response bad request because handler is empty")
    client.get(ServicePort, ServiceHostForRequest, ValidationTokenApi)
      .sendFuture
      .map(response => assert(response.statusCode().toString equals BAD_REQUEST.code().toString))
    }

  }
