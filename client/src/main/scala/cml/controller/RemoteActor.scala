package cml.controller

import java.io.File

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

class RemoteActor extends Actor{
  override def receive: Receive = {
    case msg:String =>
      println("RemoteActor receive " + msg + "from " + sender)
      sender ! msg
    case _ => println("Non ho ricevuto nulla")
  }
}

object RemoteActor {

  def main(args: Array[String])  {
    val configFile = getClass.getClassLoader.getResource("actor/remote_actor.conf").getFile
    //parse the config
    val config = ConfigFactory.parseFile(new File(configFile))
    val system = ActorSystem("CML", config)
    val remoteActor = system.actorOf(Props[RemoteActor], name="RemoteActor")
    println("------ RemoteActor is ready")
  }
}