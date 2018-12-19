package cml.services.authentication

import cml.core.{BeforeAndAfterTest, VertxTest}
import io.vertx.lang.scala.ScalaVerticle

/**
  * Abstract class implements list of verticle to deploy for test AuthenticationVerticle
  */
abstract class AuthenticationServiceTest extends VertxTest with BeforeAndAfterTest {

  override protected val serviceList: List[ScalaVerticle] = List(AuthenticationVerticle())
}
