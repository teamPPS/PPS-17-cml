package cml.controller.messages

/**
  * Village request messages
  * @author Monica Gondolini
  */
object VillageRequest {

  sealed trait VillageRequest

  /**
    * Request to create a new VIllage
    * @param username
    */
  case class CreateVillage(username: String) extends VillageRequest

  /**
    * Resuest to enter a village
    * @param username
    */
  case class EnterVillage(username: String) extends VillageRequest

  /**
    * Request to update a village
    * @param update
    */
  case class UpdateVillage(update: String) extends VillageRequest

//  case class DeleteVillage() extends VillageRequest

}
