package cml

import cml.services.authentication.AuthenticationVerticle
import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.core.Vertx

/**
  * Launcher of all Verticle Services
  *
  * @author ecavina
  */
object ServerMain extends App {

  var vertx = Vertx.vertx()

  vertx.deployVerticle(ScalaVerticle.nameForVerticle[AuthenticationVerticle])
//  vertx.deployVerticle(ScalaVerticle.nameForVerticle[VillageVerticle])
}