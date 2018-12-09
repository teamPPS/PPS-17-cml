package cml.services.village

import cml.core.TokenAuthentication
import io.vertx.scala.core.Vertx
import io.vertx.scala.ext.web.client.WebClient
import cml.core.utils.NetworkConfiguration._
import cml.services.authentication.utils.AuthenticationUrl.RegisterApi
import io.netty.handler.codec.http.HttpHeaderNames
import io.netty.handler.codec.http.HttpResponseStatus.{BAD_REQUEST, CREATED}

/**
  * This test checks if the VillageVerticle is correct.
  * @author Filippo Portolani
  */

class VillageVerticleTest extends VillageVerticleRoutingTest {

  private var vertx: Vertx = Vertx.vertx()
  private var client: WebClient = WebClient.create(vertx)

  private val username: String = "Filippo"
  private val password: String= "Test"

  private def base64AuthenticationTest(username: String, password: String): String = {
    val handler = TokenAuthentication.base64Authentication(username, password)
    handler.get
  }

  test("Bad village creation test") {
    println("Returns a bad request because handler is empty.")
    client.post(VillageServicePort, ServiceHostForRequest, RegisterApi)
      .sendFuture
      .map(response => assert(response.statusCode().toString equals BAD_REQUEST.code().toString))

    println("Returns a bad request because handler is invalid.")
    client.post(AuthenticationServicePort, ServiceHostForRequest, RegisterApi)
      .putHeader("", "")
      .sendFuture
      .map({ response => assert(response.statusCode().toString equals BAD_REQUEST.code().toString)
      })
  }

  test("Correct village creation test"){
    println("Handler is correctly created.")
    client.post(AuthenticationServicePort, ServiceHostForRequest, RegisterApi)
      .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), base64AuthenticationTest(username, password))
      .sendFuture
      .map(response =>
        assert(response.statusCode().toString equals CREATED.code().toString))
  }



}
