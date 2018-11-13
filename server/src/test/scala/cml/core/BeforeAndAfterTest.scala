package cml.core

import cml.services.authentication.AuthenticationVerticle
import io.vertx.core.Vertx
import io.vertx.lang.scala.ScalaVerticle
import org.scalatest._

class BeforeAndAfterTest extends VerticleTesting[AuthenticationVerticle] with BeforeAndAfter {

  val vertx = Vertx.vertx()

  before {
    println("service init")
    vertx.deployVerticle(ScalaVerticle.nameForVerticle[AuthenticationVerticle])
  }

}