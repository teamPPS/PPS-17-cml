package cml.controller.fx

import java.io.File

import cml.controller.actor.utils.ActorUtils.BattleActorInfo._
import cml.controller.BattleActor
import cml.view.ViewSwitch
import cml.utils.ViewConfig._
import akka.actor.{ActorRef, ActorSystem, Props}
import cml.controller.messages.BattleRequest.SceneInfo
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

  var battleActor: ActorRef = _

  import java.util.Locale
  Locale.setDefault(Locale.ENGLISH)

  @FXML var exitButton: Button = _

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
      println("context --- > " + exitButton.getScene )
      battleActor ! SceneInfo(exitButton.getScene)
    }
    //TODO: add progress indicator
  }

  private def createBattleActor(): Unit ={
    val configFile = getClass.getClassLoader.getResource(Path).getFile
    val config = ConfigFactory.parseFile(new File(configFile))
    val system = ActorSystem("LocalContext", config)
    battleActor = system.actorOf(Props[BattleActor], name=Name)
    println("------ BattleActor is ready")
  }

}
