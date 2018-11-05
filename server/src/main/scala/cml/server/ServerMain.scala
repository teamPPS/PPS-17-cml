package cml.server

import cml.services.authentication.AuthenticationVerticle
import cml.services.village.VillageVerticle
import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.core.Vertx

/**
  * Launcher of all Verticle Services
  *
  * @ecavina
  */
object ServerMain extends App {

  var vertx = Vertx.vertx()

//  How to istantiate services verticles:
//  vertx.deployVerticle(ScalaVerticle.nameForVerticle[HelloWorldVerticle])

  vertx.deployVerticle(ScalaVerticle.nameForVerticle[AuthenticationVerticle])
  vertx.deployVerticle(ScalaVerticle.nameForVerticle[VillageVerticle])
}
