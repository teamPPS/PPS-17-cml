package cml.controller.fx


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

  def initialize(): Unit = {
    battleGame.initialization()
    attackButton.setDisable(true)
    println("arena creature" + selectedCreature.get.name + "level " + selectedCreature.get.level)
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
      ViewSwitch.activate(VillageWindow.path, exitButton.getScene)
    }
  }

  @FXML
  def attackOption(): Unit = {
    battleGame.attack()
    if(battleGame._attackPoint equals 0)  attackButton.setDisable(true)
    println("attack point in attack -- " + battleGame._attackPoint)
  }

  @FXML
  def chargeOption(): Unit ={
    attackButton.setDisable(false)
    battleGame.charge()
    println("attack point in cherge -- " + battleGame._attackPoint)
  }

  @FXML
  def protectionOption(): Unit = {
    battleGame.protection()
    println("protection on: " + battleGame._isProtection())
    //todo: da rivedere questa logica
    battleGame.isProtect_()
    println("protection on: " + battleGame._isProtection())
  }

  @FXML
  def userLifeBarOption(): Unit = {
    userLifeBar.setProgress(0.25)
  }

  @FXML
  def challengerLifeBarOption(): Unit ={
    challengerLifeBar.setProgress(0.25)
  }
}
