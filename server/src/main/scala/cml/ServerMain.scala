package cml

import cml.services.authentication.AuthenticationVerticle
import cml.services.village.VillageVerticle
import io.vertx.scala.core.Vertx

import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.implicitConversions
import scala.util.{Failure, Success}

/**
  * Launcher of all Verticle Services
  *
  * @author ecavina
  * @author (modified by) Chiara Volonnino
  */
object ServerMain extends App {
  var vertx = Vertx.vertx()

  vertx.deployVerticleFuture(AuthenticationVerticle()).onComplete {
    case Success(_) =>
      println(s"Verticle authentication starting") // <3>
      vertx.deployVerticleFuture(VillageVerticle()).onComplete{
        case Success(_) => println(s"Verticle village starting") // <3>
        case Failure(exception) => exception.printStackTrace()
      }
    case Failure(exception) => exception.printStackTrace()
  }
}
