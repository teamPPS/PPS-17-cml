package cml.controller

import akka.actor.Actor
import cml.controller.actor.utils.ViewMessage.ViewVillageMessage._
import cml.controller.messages.VillageRequest.{CreateVillage, DeleteVillage, EnterVillage, UpdateVillage}
import cml.controller.messages.VillageResponse.{CreateVillageSuccess, EnterVillageSuccess, VillageFailure}
import cml.services.village.VillageServiceVertx.VillageServiceVertxImpl

import scala.util.{Failure, Success}


/**
  * Class that implements the actor which manages the village interaction with user
  * @author Monica Gondolini
//  * @param controller
  */
class VillageActor() extends Actor{ //controller: VillageViewController

  val villageVertx = VillageServiceVertxImpl(self)

  /**
    * @return village behaviour
    */
  override def receive: Receive = {
    case CreateVillage() => villageVertx.createVillage()
      .onComplete{
        case Success(httpResponse) => CreateVillageSuccess
        case Failure(exception) => VillageFailure(createFailure)
      }
    case EnterVillage() => villageVertx.enterVillage()
      .onComplete{
        case Success(httpResponse) => sender() ! EnterVillageSuccess
        case Failure(exception) => sender() ! VillageFailure(enterFailure)
      }

    case UpdateVillage(update) => villageVertx.updateVillage(update)
      .onComplete{
        case Success(httpResponse) => ??? //modificare model
        case Failure(exception) => ???
      }
    case DeleteVillage() => villageVertx.deleteVillageAndUser()
      .onComplete{
        case Success(httpResponse) => ??? //cancella tutto torna alla schermata ti autenticazione
        case Failure(exception) => ???
      }
  }
}
