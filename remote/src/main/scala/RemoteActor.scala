import java.io.File

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import cml.controller.actor.utils.ActorUtils.RemoteActorInfo._
import cml.controller.messages.ArenaRequest.RequireTurnRequest
import cml.controller.messages.ArenaResponse.RequireTurnSuccess
import cml.controller.messages.BattleRequest.{ExistChallenger, ExitRequest, RequireEnterInArena}
import cml.controller.messages.BattleResponse.{ExistChallengerSuccess, RequireEnterInArenaSuccess}
import cml.model.base.Creature
import com.typesafe.config.ConfigFactory

/**
  * This class implements remote actor utils for battle managements
  * @author Chiara Volonnino
  * @author (edited by) Monica Gondolini
  */

class RemoteActor extends Actor with ActorLogging {
  val DefaultMessage: String = "WARNING: RemoteActor has not receive anything"
  var mapActorCreature: Map[ActorRef,  Option[Creature]] = Map[ActorRef,  Option[Creature]]()
  var isFirst: Boolean = true

  override def receive: Receive = {
    case RequireEnterInArena(selectedCreature) =>
      addIntoBattleUserList(sender, selectedCreature)
      sender ! RequireEnterInArenaSuccess()
    case ExistChallenger() =>
      val exist = existChallenger()
      if (exist) mapActorCreature.foreach{ case (actor, _) => actor ! ExistChallengerSuccess(mapActorCreature) }
    case ExitRequest() =>
      removeIntoBattleUserList(sender)
    case RequireTurnRequest(attackPower, isProtected, turn) =>
      turnManagement(turn)
      sender ! RequireTurnSuccess(attackPower, isProtected, turn)
    case _ => log.info(DefaultMessage)
  }

  private def addIntoBattleUserList(actorIdentity: ActorRef, creature:  Option[Creature]){
    mapActorCreature += (actorIdentity -> creature)
    log.info("MAP add --> " + mapActorCreature)
  }

  private def removeIntoBattleUserList(actorIdentity: ActorRef) {
    mapActorCreature -= actorIdentity
    log.info("LIST remove --> " + mapActorCreature_)
  }

  //todo: technical debit
  private def existChallenger(): Boolean = {
    if (mapActorCreature_().size.equals(2)) true
    else false
  }

  private def mapActorCreature_():  Map[ActorRef,  Option[Creature]]  = {
    mapActorCreature
  }

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
// TODO: shift this main in server side (server main) if its possible add another service-like (its't really service)
  def main(args: Array[String])  {
    val configFile = getClass.getClassLoader.getResource(Configuration).getFile
    val config = ConfigFactory.parseFile(new File(configFile))
    val system = ActorSystem(Context, config)
    val remoteActor = system.actorOf(Props[RemoteActor], name=Name)
    println("------ RemoteActor is ready")
  }
}
