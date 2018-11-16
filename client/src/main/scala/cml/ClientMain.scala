package cml

import cml.utils.Configuration.{ArenaWindow, AuthenticationWindow, BattleWindow, VillageWindow}
import cml.view.ViewSwitchConfig
import javafx.application.{Application, Platform}
import javafx.fxml.FXMLLoader
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

/**
  * @author Monica Gondolini,Filippo Portolani
 */

class ClientMain extends Application{

  override def start(primaryStage: Stage): Unit = {
    val rootParent: Parent = FXMLLoader.load(getClass.getClassLoader.getResource(AuthenticationWindow.path))

    val scene : Scene = new Scene(rootParent, AuthenticationWindow.width, AuthenticationWindow.height)

    ViewSwitchConfig.scenes = Map(
      AuthenticationWindow.path -> FXMLLoader.load(getClass.getClassLoader.getResource(AuthenticationWindow.path)),
      VillageWindow.path -> FXMLLoader.load(getClass.getClassLoader.getResource(VillageWindow.path)),
      BattleWindow.path -> FXMLLoader.load(getClass.getClassLoader.getResource(BattleWindow.path)),
      ArenaWindow.path -> FXMLLoader.load(getClass.getClassLoader.getResource(ArenaWindow.path))
    )

    primaryStage.setTitle(AuthenticationWindow.title)
    primaryStage.setScene(scene)
    primaryStage.setResizable(false)
    primaryStage.setOnCloseRequest(_ => {
        Platform.exit()
        System.exit(0)
    })
    primaryStage.show()
  }
}

object Main {

  def main(args: Array[String]): Unit = {
    Application.launch(classOf[ClientMain], args: _*)
  }
}
