package cml.services.village.utils

object VillageConfig {

  object Village{
    val NAME: String = "villageName"
    val USERNAME: String = "username"
    val FOOD: String = "food"
    val GOLD: String = "gold"
    val BUILDING: String = "building"
    val HABITAT: String = "habitat"
  }

  object Building{
    val ID: String = "buildingId"
    val TYPE: String = "buildingType"
    val LEVEL: String = "buildingLevel"
  }

  object Habitat{
    val ID: String = "habitatId"
    val LEVEL: String = "habitatLevel"
    val ELEMENT: String = "element"
    val CREATURE: String = "creature"
  }

  object Creature{
    val ID: String = "creatureId"
    val NAME: String = "creatureName"
    val LEVEL: String = "creatureLevel"
    val ELEMENT: String = "element"
  }

}
