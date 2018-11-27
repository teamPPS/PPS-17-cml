package cml.controller.fx

import akka.actor.{ActorRef, Props}
import cml.controller.VillageActor
import cml.controller.actor.utils.AppActorSystem.system
import cml.controller.messages.VillageRequest.EnterVillage
import cml.view.{BaseGridInitializer, ConcreteHandlerSetup, ViewSwitch}
import cml.utils.ViewConfig._
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
  @FXML var levelUPButton: Button = _
  @FXML var upgradePane: Pane = _
  @FXML var areaPane: Pane = _
  @FXML var villagePane: ScrollPane = _
  @FXML var buildingsGrid: ScrollPane = _
  var villageMap: GridPane = _
  var buildingsMenu: GridPane = _

  val villageActor: ActorRef = system actorOf(Props(new VillageActor()), "VillageActor") //da mettere in handler dopo il merge
  //per ogni cambiamento del model manda un messaggio di update villageActor ! UpdateVillage(json)

  def initialize(): Unit = {
    settingsMenuItem setOnAction (_ => println("Pressed settings submenu button")) // open settings dialog
    logoutMenuItem setOnAction (_ => ViewSwitch.activate(AuthenticationWindow.path, logoutMenuItem.getParentPopup.getOwnerWindow.getScene))
    battleButton setOnAction (_ => ViewSwitch.activate(BattleWindow.path, battleButton.getScene))

    //mando msg a villaggio passando il modello e il controller
    villageActor ! EnterVillage(this)
  }


  def setGridAndHandlers(): Unit = {

    villageMap = new GridPane
    BaseGridInitializer.initializeVillage(villageMap)
    villagePane setContent villageMap

    ConcreteHandlerSetup.setupVillageHandlers(villageMap, areaPane, upgradePane)

    buildingsMenu = new GridPane
    BaseGridInitializer.initializeBuildingsMenu(buildingsMenu)
    buildingsGrid setContent buildingsMenu

    ConcreteHandlerSetup.setupBuildingsHandlers(buildingsMenu, areaPane, upgradePane)
  }
}
