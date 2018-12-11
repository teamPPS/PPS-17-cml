package cml.services.village

import cml.core.{BeforeAndAfterTest, VertxTest}
import cml.services.authentication.AuthenticationVerticle
import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.core.Vertx

class VillageVerticleRoutingTest extends VertxTest with BeforeAndAfterTest{

  private val vertx = Vertx.vertx()
  override protected val serviceList: List[ScalaVerticle] = List(VillageVerticle())
  override protected val  verticleToUse: Unit = vertx.deployVerticle(ScalaVerticle.nameForVerticle[AuthenticationVerticle])

}
