package cml

import java.io.File
import akka.actor.{ActorSystem, Props}
import cml.services.authentication.AuthenticationVerticle
import com.typesafe.config.ConfigFactory
import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.core.Vertx

/**
  * Launcher of all Verticle Services
  *
  * @author ecavina
  */
object ServerMain extends App {

  /*DA SCOMMENTARE QUANDO ABBIAMO FINITO E CANCELLARE IL MAIN IN REMOTEACTOR
  val configFile = getClass.getClassLoader.getResource("actor/remote_actor.conf").getFile
  val config = ConfigFactory.parseFile(new File(configFile))
  val system = ActorSystem("CML", config)
  val remoteActor = system.actorOf(Props[RemoteActor], name="RemoteActor")
  println("------ RemoteActor is ready")*/

  var vertx = Vertx.vertx()

  vertx.deployVerticle(ScalaVerticle.nameForVerticle[AuthenticationVerticle])
//  vertx.deployVerticle(ScalaVerticle.nameForVerticle[VillageVerticle])
}
