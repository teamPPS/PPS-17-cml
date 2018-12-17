package cml.controller.fx

import akka.actor.ActorSelection
import cml.controller.actor.utils.ActorUtils.ActorSystemInfo._
import cml.controller.actor.utils.ActorUtils.ActorPath.{AuthenticationActorPath, VillageActorPath}
import cml.controller.messages.AuthenticationRequest.Logout
import cml.controller.messages.VillageRequest.{DeleteVillage, EnterVillage}
import cml.model.base._
import cml.utils.ViewConfig.{AuthenticationWindow, BattleWindow}
import cml.view.{BaseGridInitializer, ConcreteHandlerSetup, ViewSwitch}
import javafx.animation.AnimationTimer
import javafx.fxml.FXML
import javafx.scene.control.Alert.AlertType
import javafx.scene.control._
import javafx.scene.layout.{GridPane, Pane}

/**
  * FX Controller for Village View
  *
  * @author ecavina
  */
class VillageViewController {

  @FXML var playerLevelLabel: Label = _
  @FXML var goldLabel: Label = _
  @FXML var foodLabel: Label = _
  @FXML var deleteMenuItem: MenuItem = _
  @FXML var logoutMenuItem: MenuItem = _
  @FXML var selectionInfo: TextArea = _
  @FXML var battleButton: Button = _
  @FXML var levelUpButton: Button = _
  @FXML var takeButton: Button = _
  @FXML var addCreatureButton: Button = _
  @FXML var upgradePane: Pane = _
  @FXML var areaPane: Pane = _
  @FXML var villagePane: ScrollPane = _
  @FXML var buildingsGrid: ScrollPane = _
  @FXML var menuButton: MenuButton = _
  var villageMap: GridPane = _
  var buildingsMenu: GridPane = _
  val villageActor: ActorSelection = system actorSelection VillageActorPath
  val authenticationActor: ActorSelection = system actorSelection AuthenticationActorPath
  var updateResourcesTimer: AnimationTimer = _

  val timeToUpdate: (Long, Long) => Boolean =
    (now: Long, lastUpdate: Long) => (now - lastUpdate) / 1000000 > 10000

  def initialize(): Unit = {
    villageActor ! EnterVillage(this)
  }

  @FXML def goToBattle(): Unit = ViewSwitch.activate(BattleWindow.path, battleButton.getScene)

  @FXML def logout(): Unit =  logoutSystem()

  @FXML def deleteAccount(): Unit = {
    val result = new Alert(AlertType.CONFIRMATION) {
      setTitle("Confirmation Dialog")
      setHeaderText("Delete Account")
      setContentText("Are you sure want to confirm?")
    }.showAndWait()

    if (result.isPresent && result.get() == ButtonType.OK) {
      villageActor ! DeleteVillage(this)
    }
  }

  def setGridAndHandlers(jsonUserVillage: String): Unit = {

    VillageMap.createVillage(PopulateVillageMapStrategy.populateVillageFromJson)(jsonUserVillage)

    playerLevelLabel.setText(VillageMap.instance().get.villageName)
    goldLabel.setText(VillageMap.instance().get.gold.toString)
    foodLabel.setText(VillageMap.instance().get.food.toString)

    villageMap = new GridPane
    BaseGridInitializer.initializeVillage(villageMap)
    villagePane setContent villageMap
    ConcreteHandlerSetup.setupVillageHandlers(villageMap, this)

    buildingsMenu = new GridPane
    BaseGridInitializer.initializeBuildingsMenu(buildingsMenu)
    buildingsGrid setContent buildingsMenu
    ConcreteHandlerSetup.setupBuildingsHandlers(buildingsMenu, this)

    updateResourcesTimer = new AnimationTimer() {
      var lastUpdate = 0L
      override def handle(now: Long): Unit = {
        if(timeToUpdate(now, lastUpdate)) {
          VillageMap.instance().get.villageStructure.foreach(s => s.resource.inc(s.level))
          lastUpdate = now
        }
      }
    }
    updateResourcesTimer.start()
  }

  def openAuthenticationView(): Unit =
    ViewSwitch.activate(AuthenticationWindow.path, deleteMenuItem.getParentPopup.getOwnerWindow.getScene)

  def logoutSystem(): Unit = {
    updateResourcesTimer.stop()
    authenticationActor ! Logout()
    ViewSwitch.activate(AuthenticationWindow.path, logoutMenuItem.getParentPopup.getOwnerWindow.getScene)
  }

}