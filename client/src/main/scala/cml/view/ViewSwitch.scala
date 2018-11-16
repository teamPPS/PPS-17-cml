package cml.view

import javafx.scene.{Parent, Scene}

/**
  * Can change FX Parent of a Scene
  */
object ViewSwitch extends Switch[String, Scene]{

  val viewMap: Map[String, Parent] = ViewSwitchConfig.scenes

  override def activate(id: String, scene: Scene): Unit = {
    scene.setRoot(viewMap.get(id) match {
      case Some(value) => value
      case _ => throw new IllegalArgumentException("View id not found")
    })
    scene.getWindow.sizeToScene()
  }
}

/**
  * Configure ViewSwitch possible root elements, then use ViewSwitch to change specific root element
  */
object ViewSwitchConfig {
  var scenes: Map[String, Parent] = Map[String, Parent]()
}