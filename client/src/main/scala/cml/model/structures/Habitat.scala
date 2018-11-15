package cml.model.structures

import cml.utils.ModelConfig.Habitat.LEVEL_INIT

/**
  * Class to create a new habitat
  * @author Monica Gondolini
  */

case class Habitat(habitatId: Int,
                   habitatPosition: Position,
                   element: String,
                   habitatLevel: Int = LEVEL_INIT,
                   creatures: List[String]) { //Al posto di string ci andrà tipo Creature

}


object Habitat{


}