package cml.utils

object ModelConfig {

  object Creature {
    val DRAGON_NAME: String = "Smaug"
    val GOLEM_NAME: String = "Astaroth"
    val KRAKEN_NAME: String = "Blagrox"
    val GRIFFIN_NAME: String = "Cerulea"
    val INITIAL_LEVEL: Int = 1
    val HEALTH_POINT: Int = 100
    val ATTACK_VALUE: Int = 5
  }

  object Building {
    val B_INIT_LEVEL: Int = 1
    val TYPE_FARM: String = "farm"
    val TYPE_CAVE: String = "cave"
  }

  object Habitat{
    val H_INIT_LEVEL: Int = 1
  }

  object Resource {
    val INC_BY_10: Int = 10
    val INIT_VALUE: Int = 0
  }

  object Elements {
    val WATER: String = "water"
    val FIRE: String = "fire"
    val EARTH: String = "earth"
    val AIR: String = "air"
  }
}
