package cml.services.authentication

import cml.core.{BeforeAndAfterTest, VertxTest}
import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.core.Vertx

abstract class AuthenticationServiceTest extends VertxTest with BeforeAndAfterTest {

  override protected val serviceList: List[ScalaVerticle] = List(AuthenticationVerticle())
}
