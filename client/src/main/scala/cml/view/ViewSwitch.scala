package cml.view

import javafx.scene.{Parent, Scene}

/**
  * Define a Trait useful to switch a view element in an ancestor given ID of the element
  * @tparam A type of id element
  * @tparam B type of ancestor element where set new element
  */
trait Switch[A,B] {

  /**
    * Activate the element identify by an id in root element
    * @param id id of a view element
    * @param ancestor entity where setting view element
    */
  def activate(id: A, ancestor: B): Unit

}

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