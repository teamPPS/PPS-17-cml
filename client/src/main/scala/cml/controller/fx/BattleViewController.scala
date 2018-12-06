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
import javafx.collections.{FXCollections, ObservableList}
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane

import scala.collection.mutable

/**
  * Controller class for graphic battle view
  *
  * @author Chiara Volonnino
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
  var obs: ObservableList[Creature] = FXCollections.observableArrayList()
  var selectedCreature: Creature = _

  def initialize(): Unit = {

    playButton setDisable true
    println("battle view")
    for (s <- village.structures) {
      if (s.creatures != null && s.creatures.nonEmpty) {
        creatures = s.creatures
        obs add creatures.head
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

    creatureList.setOnMouseClicked(_ => {
      selectedCreature = creatureList.getSelectionModel.getSelectedItem
      //TODO SETTARE IMAGEVIEW
      //creatureImage setImage

      creatureArea setText "Name: " + selectedCreature.name + "\nType: "+selectedCreature.creatureType +"\n"+
        "Creature level: " + selectedCreature.currentLevel +"\nAttack Value: " + selectedCreature.attackValue

      playButton setDisable false

    })
  }

  @FXML
  def exitOption(): Unit = {
    ViewSwitch.activate(VillageWindow.path, exitButton.getScene)
  }

  @FXML
  def creatureOption(): Unit = {
    val alert = new Alert(AlertType.CONFIRMATION) {
      setTitle("Confirmation Dialog")
      setHeaderText("Selected Creature: " + selectedCreature)
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

}
