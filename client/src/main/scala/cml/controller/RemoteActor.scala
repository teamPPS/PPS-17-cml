package cml.controller

import java.io.File

import akka.actor.{Actor, ActorSystem, Props}
import cml.controller.messages.ArenaRequest.ExitRequest
import cml.controller.messages.ArenaResponse.ExitSuccess
import cml.controller.messages.BattleRequest.RequireChallenger
import cml.controller.messages.BattleResponse.RequireChallengerSuccess
import com.typesafe.config.ConfigFactory

import scala.collection.mutable.ListBuffer

/**
  * @author Chiara Volonnino
  */


// potenzialmente da spostare in server
class RemoteActor extends Actor{

  var battleUserList = new ListBuffer[Int] // sarebbe figo fare una lista di user

  override def receive: Receive = {
    case msg:String =>
      println("RemoteActor receive " + msg + "from ")
    case RequireChallenger() =>
      addIntoBattleUserList(sender.hashCode)
      sender ! RequireChallengerSuccess()
    case ExitRequest() =>
      removeIntoBattleUserList(sender.hashCode)
      println("receive exit request by actor --> " + sender().hashCode())
      sender ! ExitSuccess()
    case _ => println("WARNING: RemoteActor has not receive anything")
  }

  private def addIntoBattleUserList(actorIdentity: Int){
    battleUserList += actorIdentity
    println("LIST add --> " + battleUserList )
  }

  private def removeIntoBattleUserList(actorIdentity: Int) {
    battleUserList -= actorIdentity
    println("LIST remove --> " + battleUserList)
  }

  private def existChallenger(): Boolean = {
    if (battleUserList.isEmpty) false
    else true
  }
}


object RemoteActor {

  def main(args: Array[String])  {
    val configFile = getClass.getClassLoader.getResource("actor/remote_actor.conf").getFile
    val config = ConfigFactory.parseFile(new File(configFile))
    val system = ActorSystem("CML", config)
    val remoteActor = system.actorOf(Props[RemoteActor], name="RemoteActor")
    println("------ RemoteActor is ready")
  }
}
