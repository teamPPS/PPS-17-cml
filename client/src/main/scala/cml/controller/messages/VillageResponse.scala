package cml.controller.messages

object VillageResponse {

  sealed trait VillageResponse

  /**
    * Response for entering the village succeeded
    */
  case class EnterVillageSuccess() extends VillageResponse
}
