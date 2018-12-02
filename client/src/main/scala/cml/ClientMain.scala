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

object ClientMain extends App {

    Application.launch(classOf[ViewMaster], args: _*)
}
