package cml.controller.messages

import io.vertx.lang.scala.json.JsonObject

/**
  * Village request messages
 *
  * @author Monica Gondolini
  */
object VillageRequest {

  sealed trait VillageRequest

  /**
    * Request to create a new VIllage
    */
  case class CreateVillage() extends VillageRequest

  /**
    * Resuest to enter a village
    */
  case class EnterVillage() extends VillageRequest

  /**
    * Request to update a village
    * @param update village user
    */
  case class UpdateVillage(update: JsonObject) extends VillageRequest //passare anche model e (facoltativo) textarea

  /**
    * Request to delete a village
    */
  case class DeleteVillage() extends VillageRequest

}
