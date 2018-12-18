package cml.view.utils

object VillageViewMessage {

  val CoordinatesMessage: (Int, Int) => String = (x, y) =>  "Coordinates (%s, %s)".format(x, y)

  val PriceMessage: (String, Int) => String =
    (description, price) => "Element selected: %s \nPrice: %d".format(description, price)

  val DragMessage: String => String = dragged => "Dragged element: %s".format(dragged)

  val DroppedMessage: (String, Int, Int) => String =
    (description, x, y) => "Dropped element: %s in %s".format(description, CoordinatesMessage(x, y))

  private val cantBuild: String = "Can't build"
  val NotHaveMoney: String = cantBuild + " if you don't have money"
  val NotHaveFood: String = cantBuild + " if you don't have food"
  val CantBuildInOldOne: String = cantBuild + " new structure where there is an old one"

  def HabitatInformation(buildingType: String, buildingLevel: Int, buildingResource: Int,
                         creatureName: String, creatureType: String, creatureLevel: Int): String =
    "%s \nCreature: %s \nType: %s \nCreature Level: %s".format(
      BuildingInformation(buildingType, buildingLevel, buildingResource),
      creatureName,
      creatureType,
      creatureLevel
    )

  val BuildingInformation: (String, Int, Int) => String =
    (buildingType, level, resource) => "Structure: %s \nLevel: %d \nResources: %s".format(buildingType, level, resource)

}
