package cml.controller

import akka.actor.Actor
import cml.controller.fx.VillageViewController
import cml.controller.messages.VillageRequest.{CreateVillage, DeleteVillage, EnterVillage, UpdateVillage}
import cml.services.village.VillageServiceVertx
import cml.services.village.VillageServiceVertx.VillageServiceVertxImpl


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
    case EnterVillage(username) => villageVertx.enterVillage(username)
    case UpdateVillage(username, update) => villageVertx.updateVillage(username,update)
    case DeleteVillage(username) => villageVertx.deleteVillageAndUser(username)
  }
}
