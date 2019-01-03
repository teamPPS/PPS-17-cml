package cml

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props, Stash}
import cml.controller.actor.utils.ActorUtils.RemoteActorInfo._
import cml.controller.messages.ArenaRequest.RequireTurnRequest
import cml.controller.messages.ArenaResponse.RequireTurnSuccess
import cml.controller.messages.BattleRequest.{ExistChallenger, ExitRequest, RequireEnterInArena}
import cml.controller.messages.BattleResponse.{ExistChallengerSuccess, RequireEnterInArenaSuccess}
import cml.view.BattleRule.{TurnManagement, TurnManagementImpl}
import scala.collection.mutable.ListBuffer

/**
  * This class implements remote actor utils for battle managements
  * @author Chiara Volonnino
  */
class BattleConnection extends Actor with Stash with ActorLogging {
  
  private var actorList: ListBuffer[ActorRef] = new ListBuffer[ActorRef]
  private val turnManagement: TurnManagement = TurnManagementImpl()

  override def preStart(): Unit = {
    turnManagement.initialization()
  }

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
      sender ! RequireTurnSuccess(attackPower, isProtected, turnManagement.changeTurn(turn))
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

  private def existChallenger(): Boolean = {
    if (userList_().size.equals(2)) true
    else false
  }

  private def userList_():  ListBuffer[ActorRef]  = {
    actorList
  }
}

object BattleConnection {
  def main(args: Array[String])  {
    import com.typesafe.config.ConfigFactory
    val config = ConfigFactory.load(Configuration)
    val system = ActorSystem(Context, config)
    system.actorOf(Props[BattleConnection], name=Name)
    println("------ cml.RemoteActor is ready")
  }
}
