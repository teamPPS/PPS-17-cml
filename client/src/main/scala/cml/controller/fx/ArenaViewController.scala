package cml.controller.fx


import cml.controller.messages.ArenaRequest.ExitRequest
import cml.utils.ViewConfig._
import cml.view.ViewSwitch
import javafx.fxml.FXML
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.{Alert, Button, ButtonType}

/**
  * Controller class for graphic arena view
  *
  * @author Chiara Volonnino
  */

class ArenaViewController {

  @FXML var exitButton: Button = _

  @FXML
  def pauseOption(): Unit = {
    val alert = new Alert(AlertType.INFORMATION) {
      setTitle("Information Dialog")
      setHeaderText("PAUSE")
      setContentText("Resume?")
    }
    alert.showAndWait()
  }

  @FXML
  def exitOption(): Unit = {
    val alert = new Alert(AlertType.CONFIRMATION) {
      setTitle("Confirmation Dialog")
      setHeaderText("Exit")
      setContentText("Are you sure to exit?")
    }

    val result = alert.showAndWait()
    if (result.isPresent && result.get() == ButtonType.OK) {
      ViewSwitch.activate(VillageWindow.path, exitButton.getScene)
    }
  }

  @FXML
  def attackOption(): Unit = {
    println("Attack")
  }
}
