package cml.controller

import javafx.fxml.FXML
import javafx.scene.control._

class VillageController {

    @FXML var playerLevelLabel: Label = _
    @FXML var goldLabel: Label = _
    @FXML var foodLabel: Label = _
    @FXML var settingsMenuItem: MenuItem = _
    @FXML var logoutMenuItem: MenuItem = _
    @FXML var aboutSelection: TextArea = _
    @FXML var battleButton: Button = _
    @FXML var villagePane: ScrollPane = _

    def initialize(): Unit = {
        settingsMenuItem setOnAction(_ => println("Pressed settings submenu button"))
        logoutMenuItem setOnAction(_ => println("Pressed logout submenu button"))
        battleButton setOnAction(_ => println("Pressed battle button"))
        villagePane setContent(new Group)
    }
}
