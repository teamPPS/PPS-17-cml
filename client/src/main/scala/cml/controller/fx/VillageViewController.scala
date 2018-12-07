package cml.controller.fx

import akka.actor.ActorSelection
import cml.controller.actor.utils.ActorUtils.ActorSystemInfo._
import cml.controller.messages.VillageRequest.EnterVillage
import cml.utils.ViewConfig._
import cml.view.{BaseGridInitializer, ConcreteHandlerSetup, ViewSwitch}
import javafx.fxml.FXML
import javafx.scene.control._
import javafx.scene.layout.{GridPane, Pane}

class VillageViewController {

  @FXML var playerLevelLabel: Label = _
  @FXML var goldLabel: Label = _
  @FXML var foodLabel: Label = _
  @FXML var settingsMenuItem: MenuItem = _
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
  var villageMap: GridPane = _
  var buildingsMenu: GridPane = _

  val villageActor: ActorSelection = system actorSelection "/user/VillageActor"
  val authenticationActor: ActorSelection = system actorSelection "/user/AuthenticationActor"

  def initialize(): Unit = {
    settingsMenuItem setOnAction (_ => println("Pressed settings submenu button")) // open settings dialog
    //logoutMenuItem setOnAction (_ => ViewSwitch.activate(AuthenticationWindow.path, logoutMenuItem.getParentPopup.getOwnerWindow.getScene))
    logoutMenuItem setOnAction (_ => logoutSystem() )
    battleButton setOnAction (_ => ViewSwitch.activate(BattleWindow.path, battleButton.getScene))

    println("village view init")
    villageActor ! EnterVillage(this)

  }


  def setGridAndHandlers(): Unit = {

    villageMap = new GridPane
    BaseGridInitializer.initializeVillage(villageMap)
    villagePane setContent villageMap

    ConcreteHandlerSetup.setupVillageHandlers(villageMap, this)

    buildingsMenu = new GridPane
    BaseGridInitializer.initializeBuildingsMenu(buildingsMenu)
    buildingsGrid setContent buildingsMenu
    ConcreteHandlerSetup.setupBuildingsHandlers(buildingsMenu, this)
  }

  def logoutSystem(): Unit = {
//    authenticationActor ! Logout()
//    ViewSwitch.activate(AuthenticationWindow.path, logoutMenuItem.getParentPopup.getOwnerWindow.getScene)
        System.exit(0)
        println("Bye!")
  }

}
