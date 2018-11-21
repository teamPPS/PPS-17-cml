package cml.services.village

import akka.actor.ActorRef
import io.vertx.scala.core.Vertx
import io.vertx.scala.ext.web.client.WebClient


/**
  * This trait describes the Village Vertx client
  * @author Monica Gondolini
  */
trait VillageServiceVertx {

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

  }
}