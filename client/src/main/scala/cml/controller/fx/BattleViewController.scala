package cml.controller.fx

import akka.actor.{ActorRef, ActorSystem, Props}
import cml.controller.BattleActor
import cml.controller.actor.utils.ActorUtils.BattleActorInfo._
import cml.controller.messages.BattleRequest.SceneInfo
import cml.model.base.{Creature, VillageMap}
import cml.utils.ModelConfig.Creature.{DRAGON, GOLEM, GRIFFIN, WATERDEMON}
import cml.utils.ModelConfig.CreatureImage.{dragonImage, golemImage, griffinImage, waterdemonImage}
import cml.utils.ViewConfig._
import cml.view.{DialogPaneUtils, ViewSwitch}
import com.typesafe.config.ConfigFactory
import javafx.collections.{FXCollections, ObservableList}
import javafx.fxml.FXML
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

  var battleActor: ActorRef = _

  import java.util.Locale
  Locale.setDefault(Locale.ENGLISH)

  @FXML var exitButton: Button = _
  @FXML var creatureViewPane: Pane = _
  @FXML var creatureList: ListView[Creature] = _
  @FXML var creatureImage: ImageView = _
  @FXML var creatureSelectionPane: Pane = _
  @FXML var creatureArea: TextArea = _
  @FXML var playButton: Button = _

  val village: VillageMap = VillageMap.instance().get
  var creatures: mutable.MutableList[Creature] = _
  var obsCreatures: ObservableList[Creature] = FXCollections.observableArrayList()
  var selectedCreature: Option[Creature] = Creature.selectedCreature

  def initialize(): Unit = {

    for (s <- village.villageStructure) {
      if (s.creatures.nonEmpty && s.creatures.get.nonEmpty) {
        creatures = s.creatures.get
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
            selectedCreature.get.level, selectedCreature.get.attackValue)
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
  def startGame(): Unit = {
    val headerText = selectedCreature.get.name + ", " + selectedCreature.get.creatureType +"\nLevel: " + selectedCreature.get.level
    val alert = DialogPaneUtils()
    alert.createConfirmationPane(headerText)
    val result = alert.showPane()
    if (result.isPresent && result.get() == ButtonType.OK) {
      createBattleActor()
      battleActor ! SceneInfo(exitButton.getScene)
    }
  }

  private def createBattleActor(): Unit ={
    val config = ConfigFactory.load(Configuration)
    val system = ActorSystem("LocalContext", config)
    battleActor = system.actorOf(Props[BattleActor], name=Name)
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
