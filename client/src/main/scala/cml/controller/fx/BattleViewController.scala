package cml.controller.fx

import java.io.File

import cml.controller.actor.utils.ActorUtils.BattleActorInfo._
import cml.controller.BattleActor
import cml.view.ViewSwitch
import cml.utils.ViewConfig._
import akka.actor.{ActorSystem, Props}
import cml.model.base.{Creature, VillageMap}
import javafx.fxml.FXML
import javafx.scene.control.Alert.AlertType
import javafx.scene.control._
import com.typesafe.config.ConfigFactory
import javafx.application.Platform
import javafx.collections.{FXCollections, ObservableList}
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
  var villageCreatures: mutable.MutableList[Creature] = _
  var obs: ObservableList[Creature] = FXCollections.observableArrayList()
  var selectedCreature: Option[Creature] = Creature.selectedCreature

  def initialize(): Unit = {

    println("c"+villageCreatures+"---OBS"+obs)
    for (s <- village.structures) {
      if (s.creatures != null && s.creatures.nonEmpty) {
        villageCreatures = s.creatures
        obs add villageCreatures.head
      }
    }

    creatureList.setItems(obs)
    creatureList.setCellFactory(_ => new ListCell[Creature]() {
      override protected def updateItem(creature: Creature, empty: Boolean): Unit = {
        super.updateItem(creature, empty)
        if (empty || creature == null || creature.creatureType == null) setText(null)
        else setText(creature.creatureType + " " + creature.name)
      }
    })

    //TODO risolvere eccezione on click se la listview è vuota
    creatureList.setOnMouseClicked(_ => {
      selectedCreature = Some(creatureList.getSelectionModel.getSelectedItem)
      Creature.setSelectedCreature(selectedCreature)

      //TODO SETTARE IMAGEVIEW
      //creatureImage setImage
      selectedCreature match{
        case None => throw new NoSuchElementException
        case _ => creatureArea setText displayText(selectedCreature.get.name, selectedCreature.get.creatureType,
            selectedCreature.get.currentLevel,selectedCreature.get.attackValue)
          playButton setDisable false
      }
    })
  }

  @FXML
  def exitOption(): Unit = {
    selectedCreature = None
    Creature.setSelectedCreature(selectedCreature)

    //TODO rimuovere oggetti dalla listview
    creatureList.getItems.removeAll(obs)

    for(c <- villageCreatures) obs.remove(c)
    println(obs)

    creatureList.setItems(obs)
    creatureList.setCellFactory(_ => new ListCell[Creature]() {
      override protected def updateItem(creature: Creature, empty: Boolean): Unit = {
        super.updateItem(creature, empty)
        if (empty || creature == null || creature.creatureType == null) setText(null)
        else setText("ERRORE")
      }
    })
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
    val text = "Name: " + selectedCreature.get.name +
      "\nType: "+selectedCreature.get.creatureType +"\n"+
      "Creature level: " + selectedCreature.get.currentLevel +
      "\nAttack Value: " + selectedCreature.get.attackValue

    text
  }

}
