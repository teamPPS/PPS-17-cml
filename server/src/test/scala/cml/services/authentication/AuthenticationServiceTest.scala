package cml.services.authentication

import cml.core.{BeforeAndAfterTest, VertxTest}
import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.core.Vertx

abstract class AuthenticationServiceTest extends VertxTest with BeforeAndAfterTest {

  private val vertx = Vertx.vertx()
  override protected val serviceList: List[ScalaVerticle] = List(AuthenticationVerticle())
  override protected val  verticleToUse: Unit = vertx.deployVerticle(ScalaVerticle.nameForVerticle[AuthenticationVerticle])
}
