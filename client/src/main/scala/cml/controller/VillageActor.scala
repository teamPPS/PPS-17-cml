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
    *
    * @return village behaviour
    */
  override def receive: Receive = {
    case CreateVillage(username) => villageVertx.createVillage(username)
/*        .onComplete{
          case Success(httpResponse) => ???
          case Failure(exception) => ???
        }*/
    case EnterVillage(username) => villageVertx.enterVillage(username)
/*      .onComplete{
        case Success(httpResponse) => ???
        case Failure(exception) => ???
      }*/
    case UpdateVillage(username, update) => villageVertx.updateVillage(username,update)
/*      .onComplete{
        case Success(httpResponse) => ???
        case Failure(exception) => ???
      }*/
    case DeleteVillage(username) => villageVertx.deleteVillageAndUser(username)
/*      .onComplete{
        case Success(httpResponse) => ???
        case Failure(exception) => ???
      }*/
  }
}
