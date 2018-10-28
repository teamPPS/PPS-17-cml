package cml


import javafx.application.{Application, Platform}
import javafx.fxml.FXMLLoader
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

/**
  * @author Monica Gondolini
 */

class ClientMain extends Application{

  val WINDOW_HEIGHT = 400
  val WINDOW_WIDTH = 600

  override def start(primaryStage: Stage): Unit = {
    val root: Parent = FXMLLoader.load(getClass.getResource("view/authentication_view.fxml"))
    val scene : Scene = new Scene(root,WINDOW_WIDTH, WINDOW_HEIGHT)
    primaryStage.setTitle("Login")
    primaryStage.setScene(scene)
    primaryStage.setResizable(false)
    primaryStage.setOnCloseRequest(_ => {
        Platform.exit()
        System.exit(0)
    })
    primaryStage.show()
  }

}
