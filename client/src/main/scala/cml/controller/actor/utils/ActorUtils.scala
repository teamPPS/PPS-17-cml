package cml.controller.actor.utils

import akka.actor.ActorSystem

object ActorUtils {

  object ActorSystemInfo {
    val system: ActorSystem = ActorSystem("cml")
  }

  object RemoteActorInfo{
    val Path = "actor/remote_actor.conf"
    val Context = "CML"
    val Name = "RemoteActor"
  }

  object BattleActorInfo{
    val Path = "actor/local_actor.conf"
    val Context = "LocalContext"
    val Name = "BattleActor"
  }

}
