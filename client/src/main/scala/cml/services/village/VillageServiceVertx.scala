package cml.services.village

import cml.core.utils.NetworkConfiguration._
import cml.services.authentication.TokenStorage
import cml.services.village.utils.VillageUrl.{SetUpdateAPI, VillagesAPI}
import io.netty.handler.codec.http.{HttpHeaderNames, HttpResponseStatus}
import io.vertx.core.logging.{Logger, LoggerFactory}
import io.vertx.lang.scala.json.JsonObject
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
    * @return successful or failed creation
    */
  def createVillage(): Future[String]

  /**
    * Enter the user's village
    * @return successful or failed enter
    */
  def enterVillage(): Future[String]

  /**
    * Update the user's village
    * @param update what to update
    * @return successful or failed update
    */
  def updateVillage(update: JsValue): Future[String]

  /**
    * Update existing items in the user's village
    * @param update values to update
    * @return successful or failed update
    */
  def setUpdateVillage(update: JsValue): Future[String]

  /**
    * Delete the user's village
    * @return successful or failed deletion
    */
  def deleteVillageAndUser(): Future[String]

}

object VillageServiceVertx{

  val vertx: Vertx = Vertx.vertx()
  var client: WebClient = WebClient.create(vertx)

  val successfulCreationResponse: Int = HttpResponseStatus.CREATED.code
  val successfulResponse: Int = HttpResponseStatus.OK.code

  val log: Logger = LoggerFactory.getLogger("Village Service")

  def apply(): VillageServiceVertx = VillageServiceVertxImpl()

  /**
    * This class implements the Village Vertx Client
    */
  case class VillageServiceVertxImpl() extends VillageServiceVertx{

    override def createVillage(): Future[String] = {
      log.info("sending create village request", None)
      client.post(AuthenticationServicePort, ServiceHostForRequest, VillagesAPI)
        .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), TokenStorage.getUserJWTToken)
        .sendFuture
        .map(r => r.statusCode match {
          case `successfulCreationResponse` => r.bodyAsString().getOrElse("")
          case _ => log.error(r.statusCode() + r.statusMessage(), None);"Not a valid request"
        })
    }

    override def enterVillage(): Future[String] = {
      log.info("sending enter village request", None)
      client.get(AuthenticationServicePort, ServiceHostForRequest, VillagesAPI)
        .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), TokenStorage.getUserJWTToken)
        .sendFuture
        .map(r => r.statusCode match {
          case `successfulResponse` => r.bodyAsString().getOrElse("")
          case _ => log.error(r.statusCode() + r.statusMessage(), None);"Not a valid request"
        })
    }

    override def updateVillage(update: JsValue): Future[String] = {
      log.info("sending update village request", None)
      val updateJsonObj = new JsonObject(update.toString())
      client.put(AuthenticationServicePort, ServiceHostForRequest, VillagesAPI)
        .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), TokenStorage.getUserJWTToken)
        .sendJsonFuture(updateJsonObj)
        .map(r => r.statusCode() match {
          case `successfulResponse` => r.bodyAsString().getOrElse("")
          case _ => log.error(r.statusCode() + r.statusMessage(), None);"Not a valid request"
        })
    }

    override def setUpdateVillage(update: JsValue): Future[String] = {
      log.info("sending set update village request", None)
      val updateJsonObj = new JsonObject(update.toString())
      client.put(AuthenticationServicePort, ServiceHostForRequest, SetUpdateAPI)
        .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), TokenStorage.getUserJWTToken)
        .sendJsonFuture(updateJsonObj)
        .map(r => r.statusCode() match {
          case `successfulResponse` => r.bodyAsString().getOrElse("")
          case _ => log.error(r.statusCode() + r.statusMessage(), None);"Not a valid request"
        })
    }

    override def deleteVillageAndUser(): Future[String] = {
      log.info("sending village and account deletion request", None)
      client.delete(AuthenticationServicePort, ServiceHostForRequest, VillagesAPI)
        .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), TokenStorage.getUserJWTToken)
        .sendFuture
        .map(r => r.statusCode match {
          case `successfulResponse` => r.bodyAsString().getOrElse("")
          case _ => log.error(r.statusCode() + r.statusMessage(), None);"Not a valid request"
        })
    }
  }
}