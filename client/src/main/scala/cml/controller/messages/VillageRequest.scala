package cml.controller.messages

/**
  * Village request messages
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
  case class UpdateVillage(update: String) extends VillageRequest

  /**
    * Request to delete a village
    */
  case class DeleteVillage() extends VillageRequest

}
