package cml.view.utils

import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.ProgressBar
import javafx.scene.layout.FlowPane
import javafx.stage.Stage

case class ProgressView() extends Application {

  override def start(primaryStage: Stage): Unit = {
    val progressBar: ProgressBar = new ProgressBar()
    //ProgressIndicator progressIndicator = new ProgressIndicator();
    val root: FlowPane = new FlowPane()
    root.setPadding(new Insets(10))
    root.setHgap(10)
    root.getChildren.addAll(progressBar)

    val scene: Scene  = new Scene(root, 400, 300)
    val stage: Stage = new Stage()
    stage.setTitle("Wait another user")

    stage.setScene(scene)
    stage.show()
  }
}
