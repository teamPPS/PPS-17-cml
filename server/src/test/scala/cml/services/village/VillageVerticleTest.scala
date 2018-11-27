package cml.services.village

import io.vertx.scala.core.Vertx
import io.vertx.scala.ext.web.client.WebClient
import org.scalatest.{BeforeAndAfterEach, FunSuite}

class VillageVerticleTest extends FunSuite with BeforeAndAfterEach {

  private var vertx: Vertx = _
  private var client: WebClient = _

  override def beforeEach() {
    vertx = Vertx.vertx()
    client = WebClient.create(vertx)
  }

  override def afterEach() {
    client.close()
    vertx.close()
  }



}
