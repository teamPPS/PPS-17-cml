package cml.controller.messages

/**
  * Village response message
  */
object VillageResponse {

  sealed trait VillageResponse

  /**
    * Response for village creation succeeded
    */
  case class CreateVillageSuccess() extends VillageResponse

  /**
    * Response for entering the village succeeded
    */
  case class EnterVillageSuccess() extends VillageResponse

  /**
    * Response for failed request
    * @param m message
    */
  case class VillageFailure(m: String) extends VillageResponse

}
