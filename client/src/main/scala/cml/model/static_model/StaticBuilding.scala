package cml.model.static_model

import cml.model.base.Structure

/**
  * This class represents a static building
  * @param bt building type
  * @param bl building level
  * @author Filippo Portolani
  */

class StaticBuilding(bt : String, var bl :Int) extends Structure{
  /**
    * Increments Structure level
    */
  override def levelUp(): Unit = bl += 1
}
