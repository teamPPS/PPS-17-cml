package cml


import javafx.application.{Application, Platform}
import javafx.fxml.FXMLLoader
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage
import cml.utils.Configuration.AuthenticationWindow

/**
  * @author Monica Gondolini,Filippo Portolani
 */

class ClientMain extends Application{

  override def start(primaryStage: Stage): Unit = {
    val root: Parent = FXMLLoader.load(getClass.getClassLoader.getResource(AuthenticationWindow.path))
    val scene : Scene = new Scene(root, AuthenticationWindow.width, AuthenticationWindow.height)
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
