package cml.utils

object ModelConfig {

  object Creature {
    val INITIAL_LEVEL: Int = 1
    val HEALTH_POINT: Int = 100
  }

  object Building {
    val LEVEL_INIT: Int = 1
    val TYPE_FARM: String = "farm"
  }

  object Habitat{
    val LEVEL_INIT: Int = 1
  }

  object Resource {
    val INC_BY_10: Int = 10
    val INIT_VALUE: Int = 0
  }

  object Elements {
    val WATER: String = "water"
    val FIRE: String = "fire"
    val HEARTH: String = "hearth"
    val AIR: String = "air"
  }
}
