package cml.controller.fx

import akka.actor.ActorSelection
import cml.controller.actor.utils.ActorUtils.ActorSystemInfo._
import cml.controller.actor.utils.ActorUtils.ActorPath.{AuthenticationActorPath, VillageActorPath}
import cml.controller.messages.AuthenticationRequest.Logout
import cml.controller.messages.VillageRequest.{DeleteVillage, EnterVillage}
import cml.model.base.Habitat.Habitat
import cml.model.base.{Cave, Farm, Structure, VillageMap}
import cml.model.creatures.{AirCreature, EarthCreature, FireCreature, WaterCreature}
import cml.schema.Village
import cml.utils.ModelConfig
import cml.utils.ViewConfig.{AuthenticationWindow, BattleWindow}
import cml.view.{BaseGridInitializer, ConcreteHandlerSetup, ViewSwitch}
import javafx.animation.AnimationTimer
import javafx.fxml.FXML
import javafx.scene.control.Alert.AlertType
import javafx.scene.control._
import javafx.scene.layout.{GridPane, Pane}
import play.api.libs.json._

import scala.collection.mutable

/**
  * FX Controller for Village View
  *
  * @author ecavina, Monica Gondolini
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

    val json: JsValue = Json.parse(jsonUserVillage)
    val gold: JsValue = (json \ Village.GOLD_FIELD).get
    val food: JsValue = (json \ Village.FOOD_FIELD).get
    val villageName = (json \ Village.VILLAGE_NAME_FIELD).get.toString()

    playerLevelLabel.setText(villageName)
    goldLabel.setText(gold.toString())
    foodLabel.setText(food.toString())
    VillageMap.initVillage(
      structures = mutable.MutableList[Structure](),
      gold = gold.toString().toInt,
      food = food.toString().toInt,
      user = villageName
    )

    val buildings = (json \\ Village.SINGLE_BUILDING_FIELD).map(_.as[JsObject])
    for (
      building <- buildings;
      buildType <- building \\ Village.BUILDING_TYPE_FIELD;
      specificStructure = buildType.as[String] match {
        case ModelConfig.StructureType.CAVE => building.as[Cave]
        case ModelConfig.StructureType.FARM => building.as[Farm]
      }
    ) yield VillageMap.instance().get.villageStructure += specificStructure

    val habitats = (json \\ Village.SINGLE_HABITAT_FIELD).map(_.as[JsObject])
    //TODO unire questi for comprehnsion ???
    for (
      habitat <- habitats;
      specificHabitat = habitat.as[Habitat];
      creature <- (habitat \\ Village.SINGLE_CREATURE_FIELD).map(_.as[JsObject]);
      creatureType <- creature \\ Village.CREATURE_TYPE_FIELD;
      specificCreature = creatureType.as[String] match {
        case ModelConfig.Creature.DRAGON => creature.as[FireCreature]
        case ModelConfig.Creature.GOLEM => creature.as[EarthCreature]
        case ModelConfig.Creature.GRIFFIN => creature.as[AirCreature]
        case ModelConfig.Creature.WATERDEMON => creature.as[WaterCreature]
      }
    ) yield {
      specificHabitat.creatureList += specificCreature
      VillageMap.instance().get.villageStructure += specificHabitat
    }
    for (
      habitat <- habitats
      if (habitat \\ Village.SINGLE_CREATURE_FIELD).map(_.as[JsObject]).isEmpty;
      specificHabitat = habitat.as[Habitat]
    ) yield {
      VillageMap.instance().get.villageStructure += specificHabitat
    }

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

  def openAuthenticationView(): Unit = ViewSwitch.activate(AuthenticationWindow.path, deleteMenuItem.getParentPopup.getOwnerWindow.getScene)

  def logoutSystem(): Unit = {
    updateResourcesTimer.stop()
    authenticationActor ! Logout()
    ViewSwitch.activate(AuthenticationWindow.path, logoutMenuItem.getParentPopup.getOwnerWindow.getScene)
  }

}
