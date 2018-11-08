package cml.controller

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.{Alert, Button, ButtonType}

/**
  * Controller class for graphic battle view
  *
  * @author Chiara Volonnino
  */

class BattleViewController {

  @FXML var exitButton: Button = _
  @FXML var creatureButton: Button = _ // si può fare la lista? poco funzionale?
  import java.util.Locale
  Locale.setDefault(Locale.ENGLISH)


  def initializeComponent(): Unit = {
    exitButton.setOnAction((_:ActionEvent) => exitOption())
    creatureButton.setOnAction((_: ActionEvent) => creatureOption())
  }

  @FXML
  def exitOption(): Unit = {
    println("Village view")
  }

  @FXML
  def creatureOption(): Unit = {
    val alert = new Alert(AlertType.CONFIRMATION) {
      //initOwner(s)
      setTitle("Confirmation Dialog")
      setHeaderText("Creatures List")
      setContentText("Are you sure want to confirm?")
    }

    val result = alert.showAndWait()
    if (result.isPresent && result.get() == ButtonType.OK) println("Area view")
  }

}
