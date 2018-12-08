package cml.services.village

import io.vertx.scala.core.Vertx
import io.vertx.scala.ext.web.client.WebClient
import cml.core.utils.NetworkConfiguration._
import cml.services.authentication.utils.AuthenticationUrl.RegisterApi
import io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST

/**
  * This test checks if the VillageVerticle is correct.
  * @author Filippo Portolani
  */

class VillageVerticleTest extends VillageVerticleRoutingTest {

  private var vertx: Vertx = Vertx.vertx()
  private var client: WebClient = WebClient.create(vertx)

  test("Village creation test") {
    println("Returns a bad request because handler is empty.")
    client.post(VillageServicePort, ServiceHostForRequest, RegisterApi)
      .sendFuture
      .map(response => assert(response.statusCode().toString equals BAD_REQUEST.code().toString))
  }



}
