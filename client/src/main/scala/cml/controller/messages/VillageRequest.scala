package cml.controller.messages

import cml.controller.fx.VillageViewController
import io.vertx.lang.scala.json.JsonObject
import play.api.libs.json.JsValue

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
  case class EnterVillage(controller: VillageViewController) extends VillageRequest

  /**
    * Request to update a village
    * @param update village user
    */
  case class UpdateVillage(update: JsValue) extends VillageRequest


  case class SetUpdateVillage(update: JsValue) extends VillageRequest

  /**
    * Request to delete a village
    */
  case class DeleteVillage() extends VillageRequest

  case class Logout() extends VillageRequest

}
