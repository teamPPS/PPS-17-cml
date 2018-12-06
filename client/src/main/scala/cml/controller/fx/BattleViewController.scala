package cml.controller.fx

import java.io.File

import akka.actor.{ActorSystem, Props}
import cml.controller.BattleActor
import cml.controller.actor.utils.ActorUtils.BattleActorInfo._
import cml.model.base.{Creature, VillageMap}
import cml.utils.ModelConfig.Creature.{DRAGON, GOLEM, GRIFFIN, WATERDEMON}
import cml.utils.ModelConfig.CreatureImage.{dragonImage, golemImage, griffinImage, waterdemonImage}
import cml.utils.ViewConfig._
import cml.view.ViewSwitch
import com.typesafe.config.ConfigFactory
import javafx.collections.{FXCollections, ObservableList}
import javafx.fxml.FXML
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.{ListView, _}
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane

import scala.collection.mutable


/**
  * Controller class for graphic battle view
  *
  * @author Chiara Volonnino
  * @author (edited by) Monica Gondolini
  */

class BattleViewController {

  import java.util.Locale
  Locale.setDefault(Locale.ENGLISH)

  @FXML var exitButton: Button = _
  @FXML var creatureViewPane: Pane = _
  @FXML var creatureList: ListView[Creature] = _
  @FXML var creatureImage: ImageView = _
  @FXML var creatureSelectionPane: Pane = _
  @FXML var creatureArea: TextArea = _
  @FXML var playButton: Button = _

  val village: VillageMap = VillageMap.village
  var creatures: mutable.MutableList[Creature] = _
  var obsCreatures: ObservableList[Creature] = FXCollections.observableArrayList()
  var selectedCreature: Option[Creature] = Creature.selectedCreature

  def initialize(): Unit = {

    for (s <- village.structures) {
      if (s.creatures != null && s.creatures.nonEmpty) {
        creatures = s.creatures
        obsCreatures add creatures.head
      }
    }

    creatureList.setItems(obsCreatures)
    creatureList.setCellFactory(_ => new ListCell[Creature]() {
      override protected def updateItem(creature: Creature, empty: Boolean): Unit = {
        super.updateItem(creature, empty)
        if (empty || creature == null || creature.creatureType == null) setText(null)
        else setText(creature.name + ", " + creature.creatureType)
      }
    })

    creatureList.setOnMouseClicked(_ => {
      selectedCreature = Some(creatureList.getSelectionModel.getSelectedItem)
      Creature.setSelectedCreature(selectedCreature)

      selectedCreature match {
        case None => throw new NoSuchElementException
        case _ => setCreatureImage(selectedCreature.get)
          creatureArea setText displayText(selectedCreature.get.name, selectedCreature.get.creatureType,
            selectedCreature.get.currentLevel,selectedCreature.get.attackValue)
          playButton setDisable false
      }
    })
  }

  @FXML
  def exitOption(): Unit = {
    selectedCreature = None
    Creature.setSelectedCreature(selectedCreature)
    ViewSwitch.activate(VillageWindow.path, exitButton.getScene)
  }

  @FXML
  def creatureOption(): Unit = {
    val alert = new Alert(AlertType.CONFIRMATION) {
      setTitle("Confirmation Dialog")
      setHeaderText(selectedCreature.get.name + ", " + selectedCreature.get.creatureType +"\nLevel: " + selectedCreature.get.level)
      setContentText("Are you sure want to confirm?")
    }

    val result = alert.showAndWait()
    if (result.isPresent && result.get() == ButtonType.OK) {
      createBattleActor()
      ViewSwitch.activate(ArenaWindow.path, exitButton.getScene)
    }
  }

  private def createBattleActor(): Unit ={
    val configFile = getClass.getClassLoader.getResource(Path).getFile
    val config = ConfigFactory.parseFile(new File(configFile))
    val system = ActorSystem("LocalContext", config)
    val battleActor = system.actorOf(Props[BattleActor], name=Name)
    println("------ BattleActor is ready")
  }

  private def displayText(name: String, creatureType: String, level: Int, attackValue: Int): String = {
    val text = "Name: " + name +
      "\nType: " + creatureType +"\n" +
      "Creature level: " + level +
      "\nAttack Value: " + attackValue

    text
  }

  private def setCreatureImage(selection: Creature): Unit = {
    selection.creatureType match {
      case DRAGON => creatureImage setImage dragonImage
      case GOLEM => creatureImage setImage golemImage
      case GRIFFIN => creatureImage setImage griffinImage
      case WATERDEMON => creatureImage setImage waterdemonImage
    }
  }

}
