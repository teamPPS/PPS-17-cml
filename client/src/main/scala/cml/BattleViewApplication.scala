package cml

import cml.utils.Configuration.BattleWindows
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
    val root: Parent = FXMLLoader.load(getClass.getResource(BattleWindows.path))
    val scene : Scene = new Scene(root, BattleWindows.width, BattleWindows.height)
    primaryStage.setTitle(BattleWindows.title)
    primaryStage.setScene(scene)
    primaryStage.setResizable(false)
    primaryStage.setOnCloseRequest(_ => {
      Platform.exit()
      System.exit(0)
    })
    primaryStage.show()
  }
}
