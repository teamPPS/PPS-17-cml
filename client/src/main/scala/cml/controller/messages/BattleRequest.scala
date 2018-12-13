package cml.controller.messages

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
    * @param creature we want to fight with
    */
  case class RequireEnterInArena(creature: Option[Creature]) extends BattleRequest

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

}
