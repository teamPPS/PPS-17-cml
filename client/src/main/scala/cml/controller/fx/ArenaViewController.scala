package cml.controller.fx

import akka.actor.ActorSelection
import cml.controller.actor.utils.ActorUtils.ActorSystemInfo.system
import cml.controller.messages.ArenaRequest.{AttackRequest, StopRequest}
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

  var battleGame: BattleRulesImpl = BattleRulesImpl()
  val selectedCreature: Option[Creature] = Creature.selectedCreature
  var powerAttack: Int = _

  val arenaActor: ActorSelection = system actorSelection "/user/ArenaActor"

  def initialize(): Unit = {
    battleGame.initialization()
    attackButton.setDisable(true)
    println("arena creature: " + selectedCreature.get.name + " level " + selectedCreature.get.level + " " +
      " Attack value " +selectedCreature.get.attackValue)
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
    if(battleGame._attackPoint equals 0)  attackButton.setDisable(true)
    powerAttack = game()
    arenaActor ! AttackRequest(powerAttack)
  }

  @FXML
  def chargeOption(): Unit ={
    attackButton.setDisable(false)
    battleGame.charge()
    powerAttack = game()
    arenaActor ! AttackRequest(powerAttack)
    battleGame.isCharge_()
  }

  @FXML
  def protectionOption(): Unit = {
    battleGame.protection()
    powerAttack = game()
    arenaActor ! AttackRequest(powerAttack)
    battleGame.isProtect_()
  }

  @FXML
  def userLifeBarOption(): Unit = {
    userLifeBar.setProgress(0.25)
  }

  @FXML
  def challengerLifeBarOption(): Unit ={
    challengerLifeBar.setProgress(0.25)
  }

  private def creatureAttackValue_(): Int = selectedCreature.get.attackValue

  private def game(): Int = {
    battleGame.gameEngine(creatureAttackValue_())
  }
}
