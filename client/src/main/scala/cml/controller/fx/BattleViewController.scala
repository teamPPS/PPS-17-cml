package cml.controller.fx

import java.io.File

import cml.controller.actor.utils.ActorUtils.BattleActorInfo._
import cml.controller.BattleActor
import cml.view.ViewSwitch
import cml.utils.ViewConfig._
import akka.actor.{ActorSystem, Props}
import cml.model.base.VillageMap
import javafx.fxml.FXML
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.{Alert, Button, ButtonType}
import com.typesafe.config.ConfigFactory

/**
  * Controller class for graphic battle view
  *
  * @author Chiara Volonnino
  */

class BattleViewController {

  import java.util.Locale
  Locale.setDefault(Locale.ENGLISH)

  @FXML var exitButton: Button = _

  val village: VillageMap = VillageMap.village

  def initialize(): Unit = {
    println("battle view")
    println(village.structures)
    for(s <- village.structures){
      if(s.creatures != null && s.creatures.nonEmpty){
        println(s.creatures)
      }
    }
  }

  @FXML
  def exitOption(): Unit = {
    ViewSwitch.activate(VillageWindow.path, exitButton.getScene)
  }

  @FXML
  def creatureOption(): Unit = {
    val alert = new Alert(AlertType.CONFIRMATION) {
      setTitle("Confirmation Dialog")
      setHeaderText("Creatures List")
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
