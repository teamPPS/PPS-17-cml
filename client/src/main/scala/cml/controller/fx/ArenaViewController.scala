package cml.controller.fx

import akka.actor.ActorSelection
import cml.controller.actor.utils.ActorUtils.ActorSystemInfo.system
import cml.controller.actor.utils.ActorUtils.ActorPath.ArenaActorPath
import cml.controller.messages.ArenaRequest.{AttackRequest, ControllerRefRequest, StopRequest}
import cml.model.base.Creature
import cml.utils.ModelConfig.Creature.{DRAGON, GOLEM, GRIFFIN, WATERDEMON}
import cml.utils.ModelConfig.CreatureImage.{dragonImage, golemImage, griffinImage, waterdemonImage}
import cml.utils.ViewConfig._
import cml.view.BattleRule.BattleRulesImpl
import cml.view.ViewSwitch
import javafx.fxml.FXML
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.{Alert, Button, ButtonType, ProgressBar}
import javafx.scene.image.ImageView



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
  @FXML var yourCreature: ImageView = _
  @FXML var enemyCreature: ImageView = _


  private val battleGame: BattleRulesImpl = BattleRulesImpl()
  private val selectedCreature: Option[Creature] = Creature.selectedCreature
  private var userPowerAttack: Int = _
  private var _creatureLife: Int = _
  private var _challengerLife: Int = _

  private val arenaActor: ActorSelection = system actorSelection ArenaActorPath

  def initialize(): Unit ={
    arenaActor ! ControllerRefRequest(this)
    battleGame.initialization()
    attackButton.setDisable(true)
    _creatureLife = battleGame.creatureLife()
    userLifeBar.setProgress(_creatureLife)
    _challengerLife = battleGame.creatureLife()
    challengerLifeBar.setProgress(_challengerLife)

    setCreatureImage(selectedCreature.get)
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
    println(" attack -- > " + userPowerAttack)
    challengerLifeBar_()
    arenaActor ! AttackRequest(userPowerAttack)
  }

  @FXML
  def chargeOption(): Unit = {
    attackButton.setDisable(false)
    battleGame.charge()
    userPowerAttack = game()
    println("charge attack -- > " + userPowerAttack)
    arenaActor ! AttackRequest(userPowerAttack)
    battleGame.isCharge_()
  }

  @FXML
  def protectionOption(): Unit = {
    battleGame.protection()
    userPowerAttack = game()
    println("protection attack -- > " + userPowerAttack)
    arenaActor ! AttackRequest(userPowerAttack)
    battleGame.isProtect_()
  }

  def userLifeBar_(challengerPowerAttack: Int): Unit = {
    println("_challengerPowerAttack ---> " + challengerPowerAttack)
    _creatureLife -= challengerPowerAttack
    println("_challengerPowerAttack ---> " + _creatureLife)
    val progress: Double = _creatureLife.toDouble / battleGame.creatureLife().toDouble
    println("progress value --> " + progress)
    userLifeBar.setProgress(progress)
  }

  def challengerLifeBar_(): Unit = {
    _challengerLife -= userPowerAttack
    println("CHALLENGER LIFE BAR: " + _challengerLife)
    val progress: Double = _challengerLife.toDouble / battleGame.creatureLife().toDouble
    challengerLifeBar.setProgress(progress)
  }

  private def creatureAttackValue_(): Int = selectedCreature.get.attackValue

  private def game(): Int = battleGame.gameEngine(creatureAttackValue_())

  private def setCreatureImage(selection: Creature): Unit = {
    selection.creatureType match {
      case DRAGON => yourCreature setImage dragonImage
      case GOLEM => yourCreature setImage golemImage
      case GRIFFIN => yourCreature setImage griffinImage
      case WATERDEMON => yourCreature setImage waterdemonImage
    }
  }

}
