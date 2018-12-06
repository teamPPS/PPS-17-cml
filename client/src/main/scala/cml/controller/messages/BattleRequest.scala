package cml.controller.messages

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
    * Request to add user into a list for require enter in battle arena
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

  case class SwitchInArenaRequest() extends BattleRequest

}
