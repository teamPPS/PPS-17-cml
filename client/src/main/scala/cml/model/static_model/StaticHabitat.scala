package cml.model.static_model

import cml.model.base.Structure

/**
  *This class represents a static habitat
  * @param e element
  * @param hl habitat level
  * @author Filippo Portolani
  */

class StaticHabitat(e: String, var hl: Int) extends Structure {
  /**
    * Increments Structure level
    */
  override def levelUp(): Unit = hl += 1
}

