package cml.controller.fx

import akka.actor.ActorSelection
import cml.controller.actor.utils.AppActorSystem.system
import cml.controller.messages.VillageRequest.EnterVillage
import cml.model.base.Habitat.Habitat
import cml.model.base._
import cml.model.creatures.{Dragon, Golem, Griffin, Kraken}
import cml.schema.Village
import cml.utils.ViewConfig._
import cml.view.{BaseGridInitializer, ConcreteHandlerSetup, ViewSwitch}
import javafx.fxml.FXML
import javafx.scene.control._
import javafx.scene.layout.{GridPane, Pane}
import play.api.libs.json._

import scala.collection.mutable

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
  @FXML var upgradePane: Pane = _
  @FXML var areaPane: Pane = _
  @FXML var villagePane: ScrollPane = _
  @FXML var buildingsGrid: ScrollPane = _
  var villageMap: GridPane = _
  var buildingsMenu: GridPane = _

  val villageActor: ActorSelection = system actorSelection "/user/VillageActor" //da mettere in handler dopo il merge
  val authenticationActor: ActorSelection = system actorSelection "/user/AuthenticationActor"
  //per ogni cambiamento del model manda un messaggio di update villageActor ! UpdateVillage(json)

  def initialize(): Unit = {
    settingsMenuItem setOnAction (_ => println("Pressed settings submenu button")) // open settings dialog
    //logoutMenuItem setOnAction (_ => ViewSwitch.activate(AuthenticationWindow.path, logoutMenuItem.getParentPopup.getOwnerWindow.getScene))
    logoutMenuItem setOnAction (_ => logoutSystem() )
    battleButton setOnAction (_ => ViewSwitch.activate(BattleWindow.path, battleButton.getScene))

    //mando msg a villaggio passando il modello e il controller
    println("village view init")
    villageActor ! EnterVillage(this)

  }


  def setGridAndHandlers(jsonUserVillage: String): Unit = {

    var json: JsValue = Json.parse(jsonUserVillage) //TODO tutto dentro a JsonMaker o  comunque utils?
    val gold = (json \ Village.GOLD_FIELD).get.toString().toInt
    val food = (json \ Village.FOOD_FIELD).get.toString().toInt
    val villageName = (json \ Village.VILLAGE_NAME_FIELD).get.toString()

    val buildings = (json \\ Village.SINGLE_BUILDING_FIELD).map(_.as[JsObject])
    VillageMap.initVillage(mutable.MutableList[Structure](), gold, food, villageName)
    for (
      building <- buildings;
      buildType <- building \\ Village.BUILDING_TYPE_FIELD;
      specificStructure = buildType.as[String] match {
        case "CAVE" => building.as[Cave]
        case "FARM" => building.as[Farm]
      }
    ) yield VillageMap.instance().get.villageStructure += specificStructure
    println("Buildings ricevute dal server: " + VillageMap.instance().get.villageStructure)

    val habitats = (json \\ Village.SINGLE_HABITAT_FIELD).map(_.as[JsObject])
    for (
      habitat <- habitats;
      specificHabitat = habitat.as[Habitat];
      creature <- (habitat \\ Village.SINGLE_CREATURE_FIELD).map(_.as[JsObject]);
      creatureType <- creature \\ Village.CREATURE_TYPE_FIELD;
      specificCreature = creatureType.as[String] match {
        case "Dragon" => creature.as[Dragon]
        case "Golem" => creature.as[Golem]
        case "Griffin" => creature.as[Griffin]
        case "Kraken" => creature.as[Kraken]
      }
    ) yield {
      VillageMap.instance().get.villageStructure += specificHabitat
    }
    println("Buildings e Habitat ricevuti dal server: " + VillageMap.instance().get.villageStructure)

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
//    authenticationActor ! Logout() //TODO non funziona niente dopo queste operazioni di logout perché l'authentication actor ha il riferimento al vecchio controller fx
//    ViewSwitch.activate(AuthenticationWindow.path, logoutMenuItem.getParentPopup.getOwnerWindow.getScene)
        System.exit(0)
        println("Bye!")
  }

}
