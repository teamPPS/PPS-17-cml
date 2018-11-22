package cml.controller.messages

/**
  * Village request messages
  * @author Monica Gondolini
  */
object VillageRequest {

  sealed trait VillageRequest

  /**
    * Request to create a new VIllage
    * @param username village user
    */
  case class CreateVillage(username: String) extends VillageRequest

  /**
    * Resuest to enter a village
    * @param username village user
    */
  case class EnterVillage(username: String) extends VillageRequest

  /**
    * Request to update a village
    * @param username village user
    * @param update village user
    */
  case class UpdateVillage(username:String, update: String) extends VillageRequest

  /**
    * Request to delete a village
    * @param username village user
    */
  case class DeleteVillage(username: String) extends VillageRequest

}
