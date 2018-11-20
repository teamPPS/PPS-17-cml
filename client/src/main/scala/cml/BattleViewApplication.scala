package cml

import cml.utils.Configuration.BattleWindow
import javafx.application.{Application, Platform}
import javafx.fxml.FXMLLoader
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

/**
  * Main Class for test Battle view
  *
  * @author Chiara Volonnino
  */

class BattleViewApplication extends Application {
  override def start(primaryStage: Stage): Unit = {
    val root: Parent = FXMLLoader.load(getClass.getClassLoader.getResource(BattleWindow.path))
    val scene : Scene = new Scene(root, BattleWindow.width, BattleWindow.height)
    primaryStage.setTitle(BattleWindow.title)
    primaryStage.setScene(scene)
    primaryStage.setResizable(false)
    primaryStage.setOnCloseRequest(_ => {
      println("Village View")
      Platform.exit()
      System.exit(0)
    })
    primaryStage.show()
  }
}
