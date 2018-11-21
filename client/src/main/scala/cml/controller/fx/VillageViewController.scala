package cml.controller.fx

import cml.view.{BaseGridInitializer, ConcreteHandlerSetup, ViewSwitch}
import cml.utils.ViewConfig._
import javafx.fxml.FXML
import javafx.scene.control._
import javafx.scene.layout.GridPane

class VillageViewController {

  @FXML var playerLevelLabel: Label = _
  @FXML var goldLabel: Label = _
  @FXML var foodLabel: Label = _
  @FXML var settingsMenuItem: MenuItem = _
  @FXML var logoutMenuItem: MenuItem = _
  @FXML var aboutSelection: TextArea = _
  @FXML var battleButton: Button = _
  @FXML var villagePane: ScrollPane = _
  @FXML var buildingsGrid: ScrollPane = _
  var villageMap: GridPane = _
  var buildingsMenu: GridPane = _

  def initialize(): Unit = {
    settingsMenuItem setOnAction (_ => println("Pressed settings submenu button")) // open settings dialog
    logoutMenuItem setOnAction (_ => ViewSwitch.activate(AuthenticationWindow.path, logoutMenuItem.getParentPopup.getOwnerWindow.getScene))
    battleButton setOnAction (_ => ViewSwitch.activate(BattleWindow.path, battleButton.getScene))

    villageMap = new GridPane
    BaseGridInitializer.initializeVillage(villageMap)
    villagePane setContent villageMap

    ConcreteHandlerSetup.setupVillageHandlers(villageMap, aboutSelection)

    buildingsMenu = new GridPane
    BaseGridInitializer.initializeBuildingsMenu(buildingsMenu)
    buildingsGrid setContent buildingsMenu

    ConcreteHandlerSetup.setupBuildingsHandlers(buildingsMenu, aboutSelection)
  }

}
