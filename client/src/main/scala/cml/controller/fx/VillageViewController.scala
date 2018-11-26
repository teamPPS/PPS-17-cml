package cml.controller.fx

import akka.actor.{ActorRef, Props}
import cml.controller.VillageActor
import cml.controller.actor.utils.AppActorSystem.system
import cml.controller.messages.VillageRequest.EnterVillage
import cml.view.{BaseGridInitializer, ViewSwitch}
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

  val villageActor: ActorRef = system actorOf(Props(new VillageActor()), "VillageActor") //da mettere in handler dopo il merge
  //per ogni cambiamento del model manda un messaggio di update villageActor ! UpdateVillage(json)

  def initialize(): Unit = {
    settingsMenuItem setOnAction (_ => println("Pressed settings submenu button")) // open settings dialog
    logoutMenuItem setOnAction (_ => ViewSwitch.activate(AuthenticationWindow.path, logoutMenuItem.getParentPopup.getOwnerWindow.getScene))
    battleButton setOnAction (_ => ViewSwitch.activate(BattleWindow.path, battleButton.getScene))

    //mando msg a villaggio passando il modello e il controller
    villageActor ! EnterVillage() //model

    /*questa roba va in un metodo che chiamo dall'attore*/
    villageMap = new GridPane
    BaseGridInitializer.initializeVillage(villageMap)
    villagePane setContent villageMap

    buildingsMenu = new GridPane
    BaseGridInitializer.initializeBuildingsMenu(buildingsMenu)
    buildingsGrid setContent buildingsMenu
  }

}
