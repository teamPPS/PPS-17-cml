package cml.controller.actor.utils

import akka.actor.ActorSystem

object ActorUtils {

  object ActorSystemInfo {
    val system: ActorSystem = ActorSystem("cml")
  }

  object RemoteActorInfo{
    val Configuration = "remote_actor.conf"
    val Context = "CML"
    val Name = "RemoteActor"
    val Path = "akka.tcp://CML@127.0.0.1:5150/user/RemoteActor"
  }

  object BattleActorInfo{
    val Configuration = "actor/local_actor.conf"
    val Context = "LocalContext"
    val Name = "BattleActor"
  }


  object ActorPath {

  }

}
