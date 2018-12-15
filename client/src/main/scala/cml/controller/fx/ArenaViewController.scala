package cml.controller.fx

import akka.actor.ActorSelection
import cml.controller.actor.utils.ActorUtils.ActorPath.ArenaActorPath
import cml.controller.actor.utils.ActorUtils.ActorSystemInfo.system
import cml.controller.messages.ArenaRequest._
import cml.model.base.Creature
import cml.utils.ModelConfig.Creature.{DRAGON, GOLEM, GRIFFIN, WATERDEMON}
import cml.utils.ModelConfig.CreatureImage.{dragonImage, golemImage, griffinImage, waterdemonImage}
import cml.view.BattleRule.BattleRulesImpl
import cml.view.DialogPaneUtils
import javafx.fxml.FXML
import javafx.scene.control.{Button, ButtonType, ProgressBar}
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
  private var _isProtected: Boolean = _
  private var _challengerIsProtected: Boolean = _
  private var _turn: Int = _
  private var currentTurn: Int = _

  private val arenaActor: ActorSelection = system actorSelection ArenaActorPath

  def initialize(): Unit = {
    arenaActor ! ControllerRefRequest(this)
    battleGame.initialization()
    attackButton.setDisable(true)
    _creatureLife = battleGame.creatureLife()
    userLifeBar.setProgress(_creatureLife)
    _challengerLife = battleGame.creatureLife()
    challengerLifeBar.setProgress(_challengerLife)
    myCreature(selectedCreature.get)
    _isProtected = false
  }

  @FXML
  def exitOption(): Unit = {
    val headerText = "Exit"
    val contentTest = ""
    val alert = DialogPaneUtils()
    alert.crateInformationPane(headerText, contentTest)
    val result = alert.showPane()
    if (result.isPresent && result.get() == ButtonType.OK) {
      Creature.setSelectedCreature(None)
      closeOption()
    }
  }

  @FXML
  def attackOption(): Unit = {
    winOption()
    if(isTurn) {
      battleGame.attack()
      if(battleGame.attackPoint equals 0)  attackButton.setDisable(true)
      userPowerAttack = game()
      arenaActor ! AttackRequest(userPowerAttack, _isProtected)
      challengerLifeBar_()
    }
  }

  @FXML
  def chargeOption(): Unit = {
    winOption()
    if(isTurn) {
      attackButton.setDisable(false)
      battleGame.charge()
      userPowerAttack = game()
      arenaActor ! AttackRequest(userPowerAttack, _isProtected)
      challengerLifeBar_()
    }
  }

  @FXML
  def protectionOption(): Unit = {
    winOption()
    if(isTurn) {
      battleGame.protection()
      userPowerAttack = game()
      _isProtected = true
      arenaActor ! AttackRequest(userPowerAttack, _isProtected)
      challengerLifeBar_()
    }
  }

  def userLifeBar_(challengerPowerAttack: Int, challengerP: Boolean, turnValue: Int): Unit = {
    _challengerIsProtected = challengerP
    if(_isProtected)  {
      _creatureLife -= 0
      creatureState()
    }
    else _creatureLife -= challengerPowerAttack
    val progress: Double = _creatureLife.toDouble / battleGame.creatureLife().toDouble
    userLifeBar.setProgress(progress)
    currentTurn = turnValue
    winOption()
  }

  def challengerLifeBar_(): Unit = {
    if(_challengerIsProtected) userPowerAttack = 0
    _challengerLife -= userPowerAttack
    val progress: Double = _challengerLife.toDouble / battleGame.creatureLife().toDouble
    challengerLifeBar.setProgress(progress)
  }

  def challengeCreature(selection: Option[Creature]): Unit ={
    setCreatureImage(selection.get, enemyCreature)
  }

  def turn_(turnValue: Int): Unit = {
    _turn = turnValue
  }

  def closeOption(): Unit = {
    arenaActor ! StopRequest(exitButton.getScene)
  }

  private def creatureAttackValue_(): Int = selectedCreature.get.attackValue

  private def game(): Int = battleGame.gameEngine(creatureAttackValue_())

  private def setCreatureImage(selection: Creature, image: ImageView): Unit = {
    selection.creatureType match {
      case DRAGON => image setImage dragonImage
      case GOLEM => image setImage golemImage
      case GRIFFIN => image setImage griffinImage
      case WATERDEMON => image setImage waterdemonImage
    }
  }

  private def myCreature(selection: Creature): Unit = {
    setCreatureImage(selection, yourCreature)
    arenaActor ! ChallengerCreatureRequire()
  }

  private def isTurn: Boolean = {
    val tmp = _turn equals currentTurn
    tmp
  }

  private def creatureState() : Unit = {
    _isProtected = false
  }

  private def winOption(): Unit ={
    if(_creatureLife <= 0 || _challengerLife <= 0) {
      arenaActor ! StopRequest(exitButton.getScene)
    }
  }
}
