package cml.controller.fx

import akka.actor.ActorSelection
import cml.controller.ArenaActor
import cml.controller.actor.utils.ActorUtils.ActorSystemInfo.system
import cml.controller.messages.ArenaRequest.{AttackRequest, ControllerRefRequest, StopRequest}
import cml.model.base.Creature
import cml.utils.ViewConfig._
import cml.view.BattleRule.BattleRulesImpl
import cml.view.ViewSwitch
import javafx.fxml.FXML
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.{Alert, Button, ButtonType, ProgressBar}

/**
  * Controller class for graphic arena view
  *
  * @author Chiara Volonnino
  */

class ArenaViewController {

  @FXML var exitButton: Button = _
  @FXML var userLifeBar: ProgressBar = _
  @FXML var challengerLifeBar: ProgressBar = _
  @FXML var attackButton: Button = _

  private val battleGame: BattleRulesImpl = BattleRulesImpl()
  private val selectedCreature: Option[Creature] = Creature.selectedCreature
  private var userPowerAttack: Int = _
  private var _creatureLife: Int = _
  private var _challengerLife: Int = _
  private var _isProtected: Boolean = _

  private var dd: Boolean = _

  private val arenaActor: ActorSelection = system actorSelection "/user/ArenaActor"

  def initialize(): Unit ={
    arenaActor ! ControllerRefRequest(this)
    battleGame.initialization()
    attackButton.setDisable(true)
    _creatureLife = battleGame.creatureLife()
    userLifeBar.setProgress(_creatureLife)
    _challengerLife = battleGame.creatureLife()
    challengerLifeBar.setProgress(_challengerLife)
    _isProtected = false
  }

  @FXML
  def pauseOption(): Unit = {
    val alert = new Alert(AlertType.INFORMATION) {
      setTitle("Information Dialog")
      setHeaderText("PAUSE")
      setContentText("Resume?")
    }
    alert.showAndWait()
  }

  @FXML
  def exitOption(): Unit = {
    val alert = new Alert(AlertType.CONFIRMATION) {
      setTitle("Confirmation Dialog")
      setHeaderText("Exit")
      setContentText("Are you sure to exit?")
    }

    val result = alert.showAndWait()
    if (result.isPresent && result.get() == ButtonType.OK) {
      Creature.setSelectedCreature(None)
      arenaActor ! StopRequest()
      ViewSwitch.activate(VillageWindow.path, exitButton.getScene)
    }
  }

  @FXML
  def attackOption(): Unit = {
    battleGame.attack()
    if(battleGame.attackPoint equals 0)  attackButton.setDisable(true)
    userPowerAttack = game()
    arenaActor ! AttackRequest(userPowerAttack, _isProtected)
    challengerLifeBar_()
  }

  @FXML
  def chargeOption(): Unit = {
    attackButton.setDisable(false)
    battleGame.charge()
    userPowerAttack = game()
    arenaActor ! AttackRequest(userPowerAttack, _isProtected)
    challengerLifeBar_()
  }

  @FXML
  def protectionOption(): Unit = {
    battleGame.protection()
    userPowerAttack = game()
    _isProtected = true
    arenaActor ! AttackRequest(userPowerAttack, _isProtected)
    challengerLifeBar_()
  }

  def userLifeBar_(challengerPowerAttack: Int, challengerP: Boolean): Unit = {
    dd=challengerP
    if(_isProtected)  {
      _creatureLife -= 0
      isProtected_
    }
    else _creatureLife -= challengerPowerAttack
    println("USER LIFE BAR: " + _challengerLife)
    val progress: Double = _creatureLife.toDouble / battleGame.creatureLife().toDouble
    userLifeBar.setProgress(progress)
  }

  def challengerLifeBar_(): Unit = {
    if(dd) userPowerAttack = 0
    _challengerLife -= userPowerAttack
    println("CHALLENGER LIFE BAR: " + _challengerLife)
    val progress: Double = _challengerLife.toDouble / battleGame.creatureLife().toDouble
    challengerLifeBar.setProgress(progress)
  }

  private def creatureAttackValue_(): Int = selectedCreature.get.attackValue

  private def game(): Int = battleGame.gameEngine(creatureAttackValue_())

  private def isProtected_ : Unit = {
    _isProtected = false
  }

}
