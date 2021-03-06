package cml.utils

import javafx.scene.image.Image

/**
  * Utils for model configuration
  */

object ModelConfig {

  object Creature {
    val WATERDEMON: String = "WaterDemon"
    val DRAGON: String = "Dragon"
    val GRIFFIN: String = "Griffin"
    val GOLEM: String = "Golem"
    val DRAGON_NAME: String = "Smaug"
    val DRAGON2_NAME: String = "Saphira"
    val GOLEM_NAME: String = "Astaroth"
    val GOLEM2_NAME: String = "Alduin"
    val WATERDEMON_NAME: String = "Blagrox"
    val GRIFFIN_NAME: String = "Cerulea"
    val INITIAL_LEVEL: Int = 1
    val HEALTH_POINT: Int = 100
    val ATTACK_VALUE: Int = 5
  }

  object Building {
    val B_INIT_LEVEL: Int = 1
    val BUILDING: String = "BUILDING"
  }

  object Habitat{
    val H_INIT_LEVEL: Int = 1
    val HABITAT: String = "HABITAT"
  }

  object Resource {
    val INC_BY_10: Int = 10
    val INIT_VALUE: Int = 0
    val FOOD: String = "Food"
    val MONEY: String = "Money"
  }

  object Elements {
    val WATER: String = "water"
    val FIRE: String = "fire"
    val EARTH: String = "earth"
    val AIR: String = "air"
  }

  object ModelClass {
    val FARM_CLASS: String = "cml.model.base.Farm"
    val CAVE_CLASS: String = "cml.model.base.Cave"
    val HABITAT_CLASS: String = "cml.model.base.Habitat$Habitat"
    val FOOD_CLASS: String = "cml.model.base.Food"
    val MONEY_CLASS: String = "cml.model.base.Money"
  }

  object StructureType{
    val FARM: String = "FARM"
    val CAVE: String = "CAVE"
    val FIRE_HABITAT: String = "FIRE_HABITAT"
    val WATER_HABITAT: String = "WATER_HABITAT"
    val EARTH_HABITAT: String = "EARTH_HABITAT"
    val AIR_HABITAT: String = "AIR_HABITAT"
  }

  object CreatureImage {
    val dragonImage: Image = new Image(getClass.getClassLoader.getResource("image/dragon.png").toString, false)
    val golemImage: Image = new Image(getClass.getClassLoader.getResource("image/golem.png").toString, false)
    val griffinImage: Image = new Image(getClass.getClassLoader.getResource("image/griffin.png").toString, false)
    val waterdemonImage: Image = new Image(getClass.getClassLoader.getResource("image/waterdemon.png").toString, false)
  }

}
