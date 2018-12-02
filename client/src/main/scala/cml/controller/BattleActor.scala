package cml.controller

import java.io.File

import akka.actor.{Actor, ActorSystem, Props}
import cml.controller.messages.BattleRequest._
import com.typesafe.config.ConfigFactory

class BattleActor extends Actor {

  @throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    val remoteActor = context.actorSelection("akka.tcp://CML@127.0.0.1:5150/user/RemoteActor")
    remoteActor ! "hello ---- "
  }
  override def receive: Receive = {
    case msg: String =>
      println("BattleActor send success  -> " + msg)
  }
}


object BattleActor {

  def main(args: Array[String]) {
    val configFile = getClass.getClassLoader.getResource("actor/local_actor.conf").getFile
    val config = ConfigFactory.parseFile(new File(configFile))
    val system = ActorSystem("LocalContext", config)
    val battleActor = system.actorOf(Props[BattleActor], name="BattleActor")


    println("------ BattleActor is ready")
  }

}
