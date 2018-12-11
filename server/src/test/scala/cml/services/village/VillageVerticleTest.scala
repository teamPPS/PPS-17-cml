package cml.services.village

import cml.core.TokenAuthentication
import io.vertx.scala.core.Vertx
import io.vertx.scala.ext.web.client.WebClient
import cml.core.utils.NetworkConfiguration._
import io.netty.handler.codec.http.HttpHeaderNames
import io.netty.handler.codec.http.HttpResponseStatus.{BAD_REQUEST, CREATED, UNAUTHORIZED, OK}
import cml.services.village.utils.VillageUrl._

/**
  * This test checks if VillageVerticle is correct.
  * @author Filippo Portolani
  */

class VillageVerticleTest extends VillageVerticleRoutingTest {

  private var vertx: Vertx = Vertx.vertx()
  private var client: WebClient = WebClient.create(vertx)

  private val username: String = "test"
  private val password: String = "test"
  private val wrongPassword: String = "fake"
  private var token: String = _

  private def base64AuthenticationTest(username: String, password: String): String = {
    val handler = TokenAuthentication.base64Authentication(username, password)
    handler.get
  }

  /*test("Bad village creation test") {
    println("Returns a bad request because handler is empty.")
    client.post(VillageServicePort, ServiceHostForRequest, VillagesAPI)
      .sendFuture
      .map(response => assert(response.statusCode().toString equals BAD_REQUEST.code().toString))

    println("Returns a bad request because handler is invalid.")
    client.post(VillageServicePort, ServiceHostForRequest, VillagesAPI)
      .putHeader("", "")
      .sendFuture
      .map({ response => assert(response.statusCode().toString equals BAD_REQUEST.code().toString)
      })
  }*/

/*
  test("Correct village creation test"){
    println("Handler is correctly created.")
    client.put(AuthenticationServicePort, ServiceHostForRequest, VillagesAPI)
      .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), base64AuthenticationTest(username, password))
    token = base64AuthenticationTest(username, password)
    client.post(VillageServicePort, ServiceHostForRequest, VillagesAPI)
      .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), base64AuthenticationTest(username, token))
      .sendFuture()
      .map({response => assert(response.statusCode().toString equals CREATED.code().toString)})

  }
*/

  /*test("Bad village login test"){
    //bad request because handler is empty
    client.put(VillageServicePort, ServiceHostForRequest, VillagesAPI)
      .sendFuture
      .map(response => assert(response.statusCode().toString equals BAD_REQUEST.code().toString))

    //bad request because handler is incorrect
    client.put(VillageServicePort, ServiceHostForRequest, VillagesAPI)
      .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), base64AuthenticationTest(username, wrongPassword))
      .sendFuture
      .map(response => assert(response.statusCode().toString equals UNAUTHORIZED.code().toString))
  }*/

  /*test("Correct village login test"){
    //handler is correctly created
    client.put(VillageServicePort, ServiceHostForRequest, VillagesAPI)
      .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), username)
      .sendFuture
      .map(response => {
        assert(response.statusCode().toString equals OK.code().toString)
      })
  }*/





}
