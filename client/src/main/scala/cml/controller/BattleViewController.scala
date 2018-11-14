package cml.controller

import cml.utils.Configuration.ArenaWindow
import javafx.application.Platform
import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.{Parent, Scene}
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.{Alert, ButtonType}
import javafx.stage.Stage

/**
  * Controller class for graphic battle view
  *
  * @author Chiara Volonnino
  */

class BattleViewController {

  import java.util.Locale
  Locale.setDefault(Locale.ENGLISH)

  @FXML
  def exitOption(): Unit = {
    println("Village view")
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

      val primaryStage: Stage = new Stage()
      val root: Parent = FXMLLoader.load(getClass.getClassLoader.getResource(ArenaWindow.path))
      val scene : Scene = new Scene(root, ArenaWindow.width, ArenaWindow.height)

      primaryStage.setTitle(ArenaWindow.title)
      primaryStage.setScene(scene)
      primaryStage.setResizable(false)
      primaryStage.setOnCloseRequest(_ => {
        Platform.exit()
        System.exit(0)
      })
      primaryStage.show()
    }
  }

}
