package cml.controller

import javafx.fxml.FXML
import javafx.scene.control._
import javafx.scene.layout.GridPane

class VillageController {

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
    logoutMenuItem setOnAction (_ => println("Pressed logout submenu button")) // logout and go back to auth scene
    battleButton setOnAction (_ => println("Pressed battle button")) // open battle scene

    villageMap = new GridPane
    villageMap setHgap 10
    villageMap setVgap 10
    villagePane setContent villageMap
    initVillageMap()

    buildingsMenu = new GridPane
    buildingsMenu setHgap 5
    buildingsMenu setVgap 5
    buildingsGrid setContent buildingsMenu
    initBuildingsMenu()

  }

  // testing purpose
  def initBuildingsMenu(): Unit = {
    // builder/factory per i tile di menu, devono estendere Node, con anche gli handler
    buildingsMenu add(new Label("Farm"), 0, 0)
    buildingsMenu add(new Label("Habitat"), 0, 1)
    buildingsMenu add(new Label("Cave"), 1, 0)
  }

  // testing purpose
  def initVillageMap(): Unit = {
    // flyweight tile https://www.baeldung.com/java-flyweight https://refactoring.guru/design-patterns/flyweight/java/example

    loop(0, 20) foreach {
      case(x, y) => villageMap add(new Label("Terrain"), x, y)
    }

    def loop(s: Int, e: Int) =
      for(
        x <- s until e;
        y <- s until e
      ) yield (x, y)
  }
}
