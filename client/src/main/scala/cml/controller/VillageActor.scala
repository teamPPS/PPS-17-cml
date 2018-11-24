package cml.controller

import akka.actor.Actor
import cml.controller.fx.VillageViewController
import cml.controller.messages.VillageRequest.{CreateVillage, DeleteVillage, EnterVillage, UpdateVillage}
import cml.services.village.VillageServiceVertx
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
/*        .onComplete{
          case Success(httpResponse) => ???
          case Failure(exception) => ???
        }*/
    case EnterVillage() => villageVertx.enterVillage()
/*      .onComplete{
        case Success(httpResponse) => sender() ! EnterVillageSuccess
        case Failure(exception) => ???
      }*/

    case UpdateVillage(update) => villageVertx.updateVillage(update)
/*      .onComplete{
        case Success(httpResponse) => ???
        case Failure(exception) => ???
      }*/
    case DeleteVillage() => villageVertx.deleteVillageAndUser()
/*      .onComplete{
        case Success(httpResponse) => ???
        case Failure(exception) => ???
      }*/
  }
}
