package cml.services.village

import cml.core.{BeforeAndAfterTest, VertxTest}
import io.vertx.lang.scala.ScalaVerticle

/**
  * Abstract class implements list of verticle to deploy for test VillageVerticle
  */
class VillageVerticleRoutingTest extends VertxTest with BeforeAndAfterTest{

  override protected val serviceList: List[ScalaVerticle] = List(VillageVerticle())

}
