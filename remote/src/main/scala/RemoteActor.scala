import java.io.File

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props, Stash}
import cml.controller.actor.utils.ActorUtils.RemoteActorInfo._
import cml.controller.messages.ArenaRequest.RequireTurnRequest
import cml.controller.messages.ArenaResponse.RequireTurnSuccess
import cml.controller.messages.BattleRequest.{ExistChallenger, ExitRequest, RequireEnterInArena}
import cml.controller.messages.BattleResponse.{ExistChallengerSuccess, RequireEnterInArenaSuccess}
import com.typesafe.config.ConfigFactory

import scala.collection.mutable.ListBuffer

/**
  * This class implements remote actor utils for battle managements
  * @author Chiara Volonnino
  */

class RemoteActor extends Actor with Stash with ActorLogging {
  
  var actorList: ListBuffer[ActorRef] = new ListBuffer[ActorRef]
  var isFirst: Boolean = true

  override def receive: Receive = {
    case RequireEnterInArena() =>
      addIntoBattleUserList(sender)
      sender ! RequireEnterInArenaSuccess()
    case ExistChallenger() =>
      val exist = existChallenger()
      if (exist) actorList.foreach{ actor => actor ! ExistChallengerSuccess(actorList) }
    case ExitRequest() =>
      removeIntoBattleUserList(sender)
    case RequireTurnRequest(attackPower, isProtected, turn) =>
      turnManagement(turn)
      sender ! RequireTurnSuccess(attackPower, isProtected, turn)
    case _ => stash()
  }

  private def addIntoBattleUserList(actorIdentity: ActorRef){
    actorList += actorIdentity
    log.info("User in list (add option) -> " + userList_())
  }

  private def removeIntoBattleUserList(actorIdentity: ActorRef) {
    actorList -= actorIdentity
    log.info("User in list (remove option) -> " + userList_)
    unstashAll()
  }

  //todo: technical debit
  private def existChallenger(): Boolean = {
    if (userList_().size.equals(2)) true
    else false
  }

  private def userList_():  ListBuffer[ActorRef]  = {
    actorList
  }

  //todo: switch in actorArena all turn management
  private def turnManagement(turn: Int): Unit = {
    if(isFirst && initializeTurn(turn)) isFirst = false
    changeTurn(turn)
  }

  val First: Int = 0
  val Second: Int = 1

  private def initializeTurn(turn: Int): Boolean = {
    turn.equals(First)
  }

  private def changeTurn(turn: Int): Int = {
    var turn_ : Int = First
    if (turn equals First) turn_ = Second
    else turn_ = First
    turn_
  }
}

object RemoteActor {
  def main(args: Array[String])  {
    val configFile = getClass.getClassLoader.getResource(Configuration).getFile
    val config = ConfigFactory.parseFile(new File(configFile))
    val system = ActorSystem(Context, config)
    system.actorOf(Props[RemoteActor], name=Name)
    println("------ RemoteActor is ready")
  }
}
