package cml.utils

import javafx.scene.image.Image

object ModelConfig {

  object Creature {
    val WATERDEMON: String = "Water Demon"
    val DRAGON: String = "Dragon"
    val GRIFFIN: String = "Griffin"
    val GOLEM: String = "Golem"
    val DRAGON_NAME: String = "Smaug"
    val GOLEM_NAME: String = "Astaroth"
    val WATERDEMON_NAME: String = "Blagrox"
    val GRIFFIN_NAME: String = "Cerulea"
    val INITIAL_LEVEL: Int = 1
    val HEALTH_POINT: Int = 100
    val ATTACK_VALUE: Int = 5
  }

  object Building {
    val B_INIT_LEVEL: Int = 1
    val TYPE_FARM: String = "Farm"
    val TYPE_CAVE: String = "Cave"
  }

  object Habitat{
    val H_INIT_LEVEL: Int = 1
  }

  object Resource {
    val INC_BY_10: Int = 10
    val INIT_VALUE: Int = 100
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
    val FARM: String = "cml.model.base.Farm"
    val CAVE: String = "cml.model.base.Cave"
    val HABITAT: String = "cml.model.base.Habitat$Habitat"
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
