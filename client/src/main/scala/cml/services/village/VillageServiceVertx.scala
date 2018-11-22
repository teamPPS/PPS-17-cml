package cml.services.village

import akka.actor.ActorRef
import io.vertx.scala.core.Vertx
import io.vertx.scala.ext.web.client.WebClient

import scala.concurrent.Future


/**
  * This trait describes the Village Vertx client
  * @author Monica Gondolini
  */
trait VillageServiceVertx {

  def createVillage(username: String): Future[String]

  def enterVillage(username: String): Future[String]

  def updateVillage(username: String, update: String): Future[Unit]

  def deleteVillageAndUser(username: String): Future[Unit]

}

object VillageServiceVertx{

  val vertx: Vertx = Vertx.vertx()
  var client: WebClient = WebClient.create(vertx)

  def apply(actor: ActorRef): VillageServiceVertx = VillageServiceVertxImpl(actor)

  /**
    * This class implements the Village Vertx Client
    * @param actor
    */
  case class VillageServiceVertxImpl(actor: ActorRef) extends VillageServiceVertx{

    override def createVillage(username: String): Future[String] = ???

    override def enterVillage(username: String): Future[String] = ???

    override def updateVillage(username: String, update: String): Future[Unit] = ???

    override def deleteVillageAndUser(username: String): Future[Unit] = ???
  }
}