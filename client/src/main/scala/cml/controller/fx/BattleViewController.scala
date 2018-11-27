package cml.controller.fx

import cml.view.ViewSwitch
import javafx.fxml.FXML
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.{Alert, Button, ButtonType}
import cml.utils.ViewConfig._

/**
  * Controller class for graphic battle view
  *
  * @author Chiara Volonnino
  */

class BattleViewController {

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
      println("Arena view -----> wait another user for battle")
      ViewSwitch.activate(ArenaWindow.path, exitButton.getScene)
    }
  }

}
