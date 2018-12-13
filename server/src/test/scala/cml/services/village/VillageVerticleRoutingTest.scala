package cml.services.village

import cml.core.{BeforeAndAfterTest, VertxTest}
import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.core.Vertx

class VillageVerticleRoutingTest extends VertxTest with BeforeAndAfterTest{

  private val vertx = Vertx.vertx()
  override protected val serviceList: List[ScalaVerticle] = List(VillageVerticle())

}
