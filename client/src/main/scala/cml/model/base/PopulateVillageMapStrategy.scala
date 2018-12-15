package cml.model.base

import cml.model.base.Habitat.Habitat
import cml.model.creatures.{AirCreature, EarthCreature, FireCreature, WaterCreature}
import cml.schema.Village
import cml.utils.ModelConfig

import scala.collection.mutable

/**
  * This object can contains different strategy to populate a user village map
  */
object PopulateVillageMapStrategy {

  def populateVillageFromJson(jsonVillage: String): Unit = {
    import cml.utils.JsonReader._
    import play.api.libs.json.Json

    val parsedJsonVillage = Json.parse(jsonVillage)

    val gold: String = readJsonField(parsedJsonVillage, Village.GOLD_FIELD).toString()
    val food: String = readJsonField(parsedJsonVillage, Village.FOOD_FIELD).toString()
    val villageName: String = readJsonField(parsedJsonVillage, Village.VILLAGE_NAME_FIELD).toString()

    VillageMap.initVillage(
      structures = mutable.MutableList[Structure](),
      gold = gold.toInt,
      food = food.toInt,
      villageName = villageName
    )

    for (
      building <- recursiveReadJsonField(parsedJsonVillage, Village.SINGLE_BUILDING_FIELD);
      buildType <- recursiveReadJsonField(building,Village.BUILDING_TYPE_FIELD);
      specificStructure = buildType.as[String] match {
        case ModelConfig.StructureType.CAVE => building.as[Cave]
        case ModelConfig.StructureType.FARM => building.as[Farm]
      }
    ) yield VillageMap.instance().get.villageStructure += specificStructure

    val habitats = recursiveReadJsonField(parsedJsonVillage, Village.SINGLE_HABITAT_FIELD)
    for (
      habitat <- habitats;
      specificHabitat = habitat.as[Habitat];
      creature <- recursiveReadJsonField(habitat, Village.SINGLE_CREATURE_FIELD);
      creatureType <- recursiveReadJsonField(creature, Village.CREATURE_TYPE_FIELD);
      specificCreature = creatureType.as[String] match {
        case ModelConfig.Creature.DRAGON => creature.as[FireCreature]
        case ModelConfig.Creature.GOLEM => creature.as[EarthCreature]
        case ModelConfig.Creature.GRIFFIN => creature.as[AirCreature]
        case ModelConfig.Creature.WATERDEMON => creature.as[WaterCreature]
      }
    ) yield {
      specificHabitat.creatureList += specificCreature
      VillageMap.instance().get.villageStructure += specificHabitat
    }

    for (
      habitat <- habitats
      if !jsonFieldExist(habitat, Village.SINGLE_CREATURE_FIELD);
      specificHabitat = habitat.as[Habitat]
    ) yield {
      VillageMap.instance().get.villageStructure += specificHabitat
    }
  }

}
