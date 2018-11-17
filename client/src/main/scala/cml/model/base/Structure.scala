package cml.model.base

import cml.utils.ModelConfig.Resource.INIT_VALUE
import cml.utils.ModelConfig.Building.TYPE_FARM
/**
  * This trait defines common operations over structures
  * @author Monica Gondolini
  */
trait Structure{
  def levelUp(): Unit
}

/**
  * Implementation of structure building
  * @param buildingType type of the building
  * @param buildingPosition coordinates of the building in the village
  * @param buildingLevel level of the building
  */
case class Building(buildingType: String, buildingPosition: Position, var buildingLevel: Int) extends Structure {

  var food = Food(INIT_VALUE) //aggiungere controllo per tipo
  override def levelUp(): Unit = buildingLevel += 1
}

/**
  * Implementation of structure habitat
  * @param element of the habitat
  * @param habitatPosition coordinates of the habitat in the village
  * @param habitatLevel level of the habitat
  * @param creatures list of creatures living in this habitat
  */
case class Habitat(element: String, habitatPosition: Position,
                   var habitatLevel: Int,creatures: List[String]) extends Structure {//Al posto di String ci andrà tipo Creature

  val money = Money(INIT_VALUE)
  override def levelUp(): Unit = habitatLevel += 1
}

