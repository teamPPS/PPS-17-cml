package cml

import akka.actor.Props
import cml.controller.AuthenticationActor
import cml.controller.actor.utils.AppActorSystem.system
import cml.controller.fx.AuthenticationViewController
import cml.utils.ViewConfig._
import javafx.application.{Application, Platform}
import javafx.fxml.FXMLLoader
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

class ClientMain extends Application{

  override def start(primaryStage: Stage): Unit = {

    val loader = new FXMLLoader()
    val rootParent: Parent = loader.load(getClass.getClassLoader.getResource(AuthenticationWindow.path).openStream())
    val scene : Scene = new Scene(rootParent)

    system actorOf(Props(
      new AuthenticationActor(loader.getController.asInstanceOf[AuthenticationViewController]))
      , "AuthenticationActor")

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
