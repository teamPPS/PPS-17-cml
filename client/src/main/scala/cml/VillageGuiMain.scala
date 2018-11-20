package cml

import cml.utils.ViewConfig._
import javafx.application.{Application, Platform}
import javafx.fxml.FXMLLoader
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

class VillageGuiMain extends Application {

  override def start(primaryStage: Stage): Unit = {
    val root: Parent = FXMLLoader.load(getClass.getClassLoader.getResource("fxml/my_village_view.fxml"))
    val scene: Scene = new Scene(root, VillageWindow.width, VillageWindow.height)
    primaryStage.setTitle("Village")
    primaryStage.setScene(scene)
    primaryStage.setResizable(true)
    primaryStage.setOnCloseRequest(_ => {
      Platform.exit()
      System.exit(0)
    })
    primaryStage.show()
  }

}
