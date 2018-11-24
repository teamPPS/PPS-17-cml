package cml.services.village

import akka.actor.ActorRef
import io.netty.handler.codec.http.HttpResponseStatus
import io.vertx.lang.scala.json.JsonObject
import io.vertx.scala.core.Vertx
import io.vertx.scala.ext.web.client.WebClient

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


/**
  * This trait describes the Village Vertx client
  * @author Monica Gondolini
  */
trait VillageServiceVertx {

  /**
    * Create a user village
    * @return successful or failed deletion
    */
  def createVillage(): Future[String]

  /**
    * Enter the user's village
    * @return successful or failed deletion
    */
  def enterVillage(): Future[String]

  /**
    * Update the user's village
    * @param update what to update
    * @return successful or failed deletion
    */
  def updateVillage(update: JsonObject): Future[Unit]

  /**
    * Delete the user's village
    * @return successful or failed deletion
    */
  def deleteVillageAndUser(): Future[Unit]

}

object VillageServiceVertx{

  val vertx: Vertx = Vertx.vertx()
  var client: WebClient = WebClient.create(vertx)

  val successfulCreationResponse: Int = HttpResponseStatus.CREATED.code
  val successfulEnteringResponse: Int = HttpResponseStatus.OK.code
  val successfulUpdateResponse: Int = HttpResponseStatus.OK.code
//  val successfulDeleteResponse: Int = HttpResponseStatus.OK.code

  def apply(actor: ActorRef): VillageServiceVertx = VillageServiceVertxImpl(actor)

  /**
    * This class implements the Village Vertx Client
    * @param actor
    */
  case class VillageServiceVertxImpl(actor: ActorRef) extends VillageServiceVertx{

    override def createVillage(): Future[String] = {
      println(s"sending create village request") //debug
      client.post(8080, "127.0.0.1", "/api/villages/") //cambiare
        .sendFuture
        .map(r => r.statusCode match {
          case `successfulCreationResponse` => r.bodyAsString().getOrElse("")
          case _ => "Not a valid request"
        })
    }

    override def enterVillage(): Future[String] = {
      client.get(8080, "127.0.0.1", "/api/villages/") //cambiare
        .sendFuture
        .map(r => r.statusCode match {
          case `successfulEnteringResponse` => r.bodyAsString().getOrElse("")
          case _ => "Not a valid request"
        })
    }

    override def updateVillage(update: JsonObject): Future[Unit] = {
      client.put(8080, "127.0.0.1", "/api/villages/") //cambiare
        .sendJsonFuture(update)
        .map(()=>_)
    }

    override def deleteVillageAndUser(): Future[Unit] = { //passandogli il token ?
      client.delete(8080, "127.0.0.1", "/api/villages/") //cambiare
        .sendFuture
        .map(()=>_)
    }
  }
}