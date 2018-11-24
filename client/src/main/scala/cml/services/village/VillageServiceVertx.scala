package cml.services.village

import akka.actor.ActorRef
import io.netty.handler.codec.http.HttpResponseStatus
import io.vertx.lang.scala.json.JsonObject
import io.vertx.scala.core.Vertx
import io.vertx.scala.ext.web.client.WebClient

import scala.concurrent.Future


/**
  * This trait describes the Village Vertx client
  * @author Monica Gondolini
  */
trait VillageServiceVertx {

  /**
    * Create a user village
    * @param username used to create the corresponding village
    * @return successful or failed deletion
    */
  def createVillage(username: String): Future[String]

  /**
    * Enter the user's village
    * @param username used to find the corresponding village
    * @return successful or failed deletion
    */
  def enterVillage(username: String): Future[String]

  /**
    * Update the user's village
    * @param username used to create the corresponding village
    * @param update what to update
    * @return successful or failed deletion
    */
  def updateVillage(username: String, update: String): Future[Unit]

  /**
    * Delete the user's village
    * @param username used to find the corresponding village
    * @return successful or failed deletion
    */
  def deleteVillageAndUser(username: String): Future[Unit]

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

    override def createVillage(username: String): Future[String] = {
      println(s"sending create village request from username:$username") //debug
      client.post(8080, "127.0.0.1", "village")
        .sendJsonFuture(new JsonObject().put("username", username)) //da creare con cose del villaggio
        .map(r => r.statusCode match { //technical debt?
          case `successfulCreationResponse` => r.bodyAsString().getOrElse("")
          case _ => "Not a valid request"
        })
    }

    override def enterVillage(username: String): Future[String] = ???

    override def updateVillage(username: String, update: String): Future[Unit] = ???

    override def deleteVillageAndUser(username: String): Future[Unit] = ???
  }
}