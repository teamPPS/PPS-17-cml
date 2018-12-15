package cml.controller.messages

import akka.actor.ActorRef
import cml.controller.messages.ArenaRequest.ArenaRequest
import cml.model.base.Creature
import javafx.scene.Scene

/**
  * Battle request messages
  *
  * @author Chiara Volonnino
  */

object BattleRequest {

  sealed trait BattleRequest

  /**
    * Request to switch in arena view
    *
    * @param scene scene
    */
  case class SceneInfo(scene: Scene) extends BattleRequest

  /**
    * Request to add user into a list to require enter in battle arena
    */
  case class RequireEnterInArena() extends BattleRequest

  /**
    * Request to exist challenger
    */
  case class ExistChallenger() extends BattleRequest

  /**
    * Request exit into list of wait challenger
    */
  case class ExitRequest() extends BattleRequest

  /**
    * Request to switch to Arena View
    */
  case class SwitchInArenaRequest() extends BattleRequest

  /**
    * Require creature image for sent of your challenger
    * @param creature Option[Creature]
    */
  case class CreatureRequire(creature: Option[Creature]) extends BattleRequest

  case class NotifierExit(actor: ActorRef) extends BattleRequest
}
