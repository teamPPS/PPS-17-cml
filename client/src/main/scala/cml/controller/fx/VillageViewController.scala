package cml.controller.fx

import akka.actor.ActorSelection
import cml.controller.actor.utils.ActorUtils.ActorSystemInfo._
import cml.controller.messages.VillageRequest.EnterVillage
import cml.model.base.Habitat.Habitat
import cml.model.base._
import cml.model.creatures.{Dragon, Golem, Griffin, WaterDemon}
import cml.schema.Village
import cml.utils.ModelConfig
import cml.utils.ViewConfig._
import cml.view.{BaseGridInitializer, ConcreteHandlerSetup, ViewSwitch}
import javafx.fxml.FXML
import javafx.scene.control.Alert.AlertType
import javafx.scene.control._
import javafx.scene.layout.{GridPane, Pane}
import play.api.libs.json._

import scala.collection.mutable

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
  var villageMap: GridPane = _
  var buildingsMenu: GridPane = _

  val villageActor: ActorSelection = system actorSelection "/user/VillageActor"
  val authenticationActor: ActorSelection = system actorSelection "/user/AuthenticationActor"

  def initialize(): Unit = {
//    deleteMenuItem setOnAction (_ => println("Pressed settings submenu button")) // open settings dialog
//    logoutMenuItem setOnAction (_ => logoutSystem() )
//    battleButton setOnAction (_ => ViewSwitch.activate(BattleWindow.path, battleButton.getScene))

    println("village view init")
    villageActor ! EnterVillage(this)
  }

  @FXML def goToBattle(): Unit = ViewSwitch.activate(BattleWindow.path, battleButton.getScene)

  @FXML def logout(): Unit =  logoutSystem()

  @FXML def deleteAccount(): Unit = {
    val alert = new Alert(AlertType.CONFIRMATION) {
      setTitle("Confirmation Dialog")
      setHeaderText("Delete Account")
      setContentText("Are you sure want to confirm?")
    }

    val result = alert.showAndWait()
    if (result.isPresent && result.get() == ButtonType.OK) {
      println("invio msg delete" )
//      villageActor ! Delete()
    }
  }


  def setGridAndHandlers(jsonUserVillage: String): Unit = {

    val json: JsValue = Json.parse(jsonUserVillage) //TODO tutto dentro a JsonMaker o  comunque utils?
    val gold = (json \ Village.GOLD_FIELD).get.toString()
    val food = (json \ Village.FOOD_FIELD).get.toString()
    val villageName = (json \ Village.VILLAGE_NAME_FIELD).get.toString()

    playerLevelLabel.setText(villageName)
    goldLabel.setText(gold)
    foodLabel.setText(food)

    val buildings = (json \\ Village.SINGLE_BUILDING_FIELD).map(_.as[JsObject])
    VillageMap.initVillage(mutable.MutableList[Structure](), gold.toInt, food.toInt, villageName)
    for (
      building <- buildings;
      buildType <- building \\ Village.BUILDING_TYPE_FIELD;
      specificStructure = buildType.as[String] match {
        case ModelConfig.StructureType.CAVE => building.as[Cave]
        case ModelConfig.StructureType.FARM => building.as[Farm]
      }
    ) yield VillageMap.instance().get.villageStructure += specificStructure
    println("Buildings ricevute dal server: " + VillageMap.instance().get.villageStructure)

    val habitats = (json \\ Village.SINGLE_HABITAT_FIELD).map(_.as[JsObject])
    //TODO unire questi for comprehnsion ???
    for (
      habitat <- habitats;
      specificHabitat = habitat.as[Habitat];
      creature <- (habitat \\ Village.SINGLE_CREATURE_FIELD).map(_.as[JsObject]);
      creatureType <- creature \\ Village.CREATURE_TYPE_FIELD;
      specificCreature = creatureType.as[String] match {
        case ModelConfig.Creature.DRAGON => creature.as[Dragon]
        case ModelConfig.Creature.GOLEM => creature.as[Golem]
        case ModelConfig.Creature.GRIFFIN => creature.as[Griffin]
        case ModelConfig.Creature.WATERDEMON => creature.as[WaterDemon]
      }
    ) yield {
      specificHabitat.creatureList += specificCreature
      VillageMap.instance().get.villageStructure += specificHabitat
    }
    for (
      habitat <- habitats;
      if (habitat \\ Village.SINGLE_CREATURE_FIELD).map(_.as[JsObject]).isEmpty;
      specificHabitat = habitat.as[Habitat]
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
