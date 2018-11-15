package cml.model.structures

import cml.utils.ModelConfig.Habitat.LEVEL_INIT

/**
  * @author Monica Gondolini
  */

case class Habitat(habitatId: Int,
                   habitatPosition: Position,
                   element: String,
                   habitatLevel: Int = LEVEL_INIT) {

}
