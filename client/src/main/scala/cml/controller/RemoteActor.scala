package cml.controller

import java.io.File
import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import cml.controller.messages.BattleRequest.{ExistChallenger, ExitRequest, RequireEnterInArena}
import cml.controller.messages.BattleResponse.{ExistChallengerSuccess, RequireEnterInArenaSuccess}
import cml.controller.actor.utils.ActorUtils.RemoteActorInfo._
import com.typesafe.config.ConfigFactory

import scala.collection.mutable.ListBuffer

/**
  * This class implements remote actor utils for battle managements
  * @author Chiara Volonnino
  */

class RemoteActor extends Actor with ActorLogging {

  //var battleUserList = new ListBuffer[Int] // sarebbe figo fare una lista di user
  var actorInList = new ListBuffer[ActorRef]

  override def receive: Receive = {
    case RequireEnterInArena() =>
      addIntoBattleUserList(sender)
      sender ! RequireEnterInArenaSuccess()
    case ExistChallenger() =>
      val exist = existChallenger()
      if (exist) actorInList.foreach(actor => actor ! ExistChallengerSuccess(actorInList))
    case ExitRequest() =>
      removeIntoBattleUserList(sender)
    case _ => log.info("WARNING: RemoteActor has not receive anything")
  }

  private def addIntoBattleUserList(actorIdentity: ActorRef){
    actorInList += actorIdentity
    log.info("LIST add --> " + userList_ )
  }

  private def removeIntoBattleUserList(actorIdentity: ActorRef) {
    actorInList -= actorIdentity
    log.info("LIST remove --> " + userList_)
  }

  //todo: technical debit
  private def existChallenger(): Boolean = {
    if (userList_().length.equals(2)) true
    else false
  }

  private def userList_(): ListBuffer[ActorRef] ={
    actorInList
  }
}


object RemoteActor {
// TODO: shift this main in server side (server main) if its possible add another service-like (its't really service)
  def main(args: Array[String])  {
    val configFile = getClass.getClassLoader.getResource(Configuration).getFile
    val config = ConfigFactory.parseFile(new File(configFile))
    val system = ActorSystem(Context, config)
    val remoteActor = system.actorOf(Props[RemoteActor], name=Name)
    println("------ RemoteActor is ready")
  }
}
