package cml.model.structures

import cml.utils.ModelConfig.Building.{TYPE_FARM,LEVEL_INIT}

/**
  * @author Monica Gondolini
  */
case class Building(buildingId: Int,
                    buildingType: String = TYPE_FARM,
                    buildingPosition: Position,
                    buildingLevel: Int = LEVEL_INIT) {

}
