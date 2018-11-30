package cml.services.village

import cml.core.utils.NetworkConfiguration._
import io.netty.handler.codec.http.HttpResponseStatus
import io.vertx.scala.core.Vertx
import io.vertx.scala.ext.web.client.WebClient
import play.api.libs.json.JsValue

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


/**
  * This trait describes the Village Vertx client
  * @author Monica Gondolini
  * @author (modified by) Chiara Volonnino
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
  def updateVillage(update: JsValue): Future[Unit]

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
  val successfulResponse: Int = HttpResponseStatus.OK.code


  def apply(): VillageServiceVertx = VillageServiceVertxImpl()

  /**
    * This class implements the Village Vertx Client
    */
  case class VillageServiceVertxImpl() extends VillageServiceVertx{

    override def createVillage(): Future[String] = {
      println(s"sending create village request") //debug
      client.post(AuthenticationServicePort, ServiceHostForRequest, "/api/villages/") //cambiare
        .sendFuture
        .map(r => r.statusCode match {
          case `successfulCreationResponse` => r.bodyAsString().getOrElse("")
          case _ => "Not a valid request"
        })
    }

    override def enterVillage(): Future[String] = {
      println(s"sending enter village request") //debug
      client.get(AuthenticationServicePort, ServiceHostForRequest, "/api/villages/") //cambiare
        .sendFuture
        .map(r => r.statusCode match {
          case `successfulResponse` => r.bodyAsString().getOrElse("")
          case _ => "Not a valid request"
        })
    }

    override def updateVillage(update: JsValue): Future[Unit] = {
      client.put(AuthenticationServicePort, ServiceHostForRequest, "/api/villages/") //cambiare
        .sendJsonFuture(update)
        .map(_ => ())
    }

    override def deleteVillageAndUser(): Future[Unit] = { //passandogli il token ?
      client.delete(AuthenticationServicePort, ServiceHostForRequest, "/api/villages/") //cambiare
        .sendFuture
        .map(_ => ())
    }
  }
}