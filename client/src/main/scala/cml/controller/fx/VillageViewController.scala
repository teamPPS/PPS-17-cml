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
import com.typesafe.scalalogging.Logger
import javafx.animation.AnimationTimer
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
  @FXML var menuButton: MenuButton = _
  var villageMap: GridPane = _
  var buildingsMenu: GridPane = _

  val villageActor: ActorSelection = system actorSelection VillageActorPath
  val authenticationActor: ActorSelection = system actorSelection AuthenticationActorPath
  var updateResourcesTimer: AnimationTimer = _

  private val log: Logger = Logger(classOf[VillageViewController])

  def initialize(): Unit = {
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
      villageActor ! DeleteVillage(this)
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
    log.info("Buildings ricevute dal server: " + VillageMap.instance().get.villageStructure)

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
      habitat <- habitats;
      if (habitat \\ Village.SINGLE_CREATURE_FIELD).map(_.as[JsObject]).isEmpty;
      specificHabitat = habitat.as[Habitat]
    ) yield {
      VillageMap.instance().get.villageStructure += specificHabitat
    }

    log.info("Buildings e Habitat ricevuti dal server: " + VillageMap.instance().get.villageStructure)

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
        if((now - lastUpdate) / 1000000 > 2000) {
          val villageStructures = VillageMap.instance().get.villageStructure
          villageStructures.foreach(s => s.resource.inc(s.level))
          lastUpdate = now
        }
      }
    }
    updateResourcesTimer.start()
  }

  def openAuthenticationView():Unit = ViewSwitch.activate(AuthenticationWindow.path, deleteMenuItem.getParentPopup.getOwnerWindow.getScene)

  def logoutSystem(): Unit = {
    updateResourcesTimer.stop()
    authenticationActor ! Logout()
    ViewSwitch.activate(AuthenticationWindow.path, logoutMenuItem.getParentPopup.getOwnerWindow.getScene)
  }

}
