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
  private var token: String = "fakeToken"

  private def base64AuthenticationTest(username: String, password: String): String = {
    val handler = TokenAuthentication.base64Authentication(username, password)
    handler.get
  }

  test("Bad village creation test") {
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
  }

  test("Bad enter to village test") {

    client.get(VillageServicePort, ServiceHostForRequest, VillagesAPI)
      .sendFuture
      .map(response => assert(response.statusCode().toString equals BAD_REQUEST.code().toString))

    client.get(VillageServicePort, ServiceHostForRequest, VillagesAPI)
      .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), token)
      .sendFuture
      .map(response => assert(response.statusCode().toString equals BAD_REQUEST.code().toString))
  }

}
