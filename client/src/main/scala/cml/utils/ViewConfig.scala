package cml.utils

object ViewConfig {

  private val SMALL_WIDTH: Int = 600
  private val SMALL_HEIGHT: Int = 400
  private val NORMAL_WIDTH: Int = 900
  private val NORMAL_HEIGHT: Int = 600

  object AuthenticationWindow{
    val path: String = "fxml/authentication_view.fxml"
    val title: String = "Creature Mania Legends"
    val width: Int = SMALL_WIDTH
    val height: Int = SMALL_HEIGHT
  }

  object BattleWindow {
    val path: String = "fxml/battle_view.fxml"
    val title: String = "Battle window"
    val width: Int = NORMAL_WIDTH
    val height: Int = NORMAL_HEIGHT
  }

  object ArenaWindow {
    val path: String = "fxml/arena_view.fxml"
    val title: String = "Arena window"
    val width: Int = NORMAL_WIDTH
    val height: Int = NORMAL_HEIGHT
  }

  object VillageWindow {
    val path: String = "fxml/my_village_view.fxml"
    val title: String = "Village"
    val width: Int = NORMAL_WIDTH
    val height: Int = NORMAL_HEIGHT
  }

}
