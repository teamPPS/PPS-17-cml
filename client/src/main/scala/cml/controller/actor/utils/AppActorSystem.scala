package cml.controller.actor.utils

import akka.actor.ActorSystem

object AppActorSystem {
  val system: ActorSystem = ActorSystem("CreatureManiaLegends")
}
