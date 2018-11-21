package cml.controller

import akka.actor.Actor
import cml.controller.fx.VillageViewController


/**
  * Class that implements the actor which manages the village interaction with user
  * @author Monica Gondolini
  * @param controller
  */
class VillageActor(controller: VillageViewController) extends Actor{

  /**
    *
    * @return village behaviour
    */
  override def receive: Receive = ???
}
