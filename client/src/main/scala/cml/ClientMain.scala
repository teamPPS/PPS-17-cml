package cml

import javafx.application.{Application, Platform}
import javafx.fxml.FXMLLoader
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage
import cml.utils.ViewConfig._

class ClientMain extends Application{

  override def start(primaryStage: Stage): Unit = {
    val rootParent: Parent = FXMLLoader.load(getClass.getClassLoader.getResource(AuthenticationWindow.path))

    val scene : Scene = new Scene(rootParent)

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
