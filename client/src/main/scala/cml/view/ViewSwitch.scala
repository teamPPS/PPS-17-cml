package cml.view

import javafx.fxml.FXMLLoader
import javafx.scene.Scene

/**
  * Can change FX Parent of a Scene
  * @author ecavina
  */
object ViewSwitch extends Switch[String, Scene]{

  override def activate(id: String, scene: Scene): Unit = {
    scene.setRoot(FXMLLoader.load(getClass.getClassLoader.getResource(id)))
    scene.getWindow.sizeToScene()
  }
}
