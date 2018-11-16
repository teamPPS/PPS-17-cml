package cml.view

import javafx.scene.{Parent, Scene}

/**
  * Define a Trait useful to change generics View Element given an ID
  * @tparam A
  * @tparam B
  */
trait Switch[A,B] {

  /**
    * Activate the view element id in root element
    * @param id id of a view element
    * @param root entity where setting view element
    */
  def activate(id: A, root: B): Unit

}

object ViewSwitch extends Switch[String, Scene]{

  val viewMap: Map[String, Parent] = ViewSwitchConfig.scenes

  override def activate(id: String, root: Scene): Unit = {
    root.setRoot(viewMap.get(id) match {
      case Some(value) => value
      case _ => throw new IllegalArgumentException("View id not found")
    })
  }
}

object ViewSwitchConfig {
  var scenes: Map[String, Parent] = Map[String, Parent]()
}