package cml

import akka.actor.{ActorSystem, Props}
import cml.controller.AuthenticationActor
import cml.controller.messages.AuthenticationRequest.Login
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
    val root: Parent = FXMLLoader.load(getClass.getResource("view/view.fxml"))
    val scene : Scene = new Scene(root,WINDOW_WIDTH, WINDOW_HEIGHT)
    primaryStage.setTitle("Login")
    primaryStage.setScene(scene)
    primaryStage.setResizable(false)
//    primaryStage.setOnCloseRequest(Platform.exit())
    primaryStage.show()
  }

}
