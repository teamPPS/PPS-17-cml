package cml.utils

/**
  * Configuration constants of the application
  *
  * @author Monica Gondolini,Filippo Portolani
  */
object Configuration {

  private val SMALL_WIDTH: Int = 600
  private val SMALL_HEIGHT: Int = 400
  private val NORMAL_WIDTH: Int = 900
  private val NORMAL_HEIGHT: Int = 600


  object Connection {
    val port: Int = 8080
    val host: String = "localhost" //"localhost"
    val requestUri: String = "/api/authentication/register" // "/uri"
  }

  object AuthenticationMsg {
    val loginSuccess: String = "Login succeeded!"
    val loginFailure: String = "Login denied."
    val registerSuccess: String = "Registration completed!"
    val registerFailure: String = "Error, registration not completed."
  }

  object InputControl {
    val emptyFields: String = "Some fields are empty."
    val userExp: String = "^[A-Za-z0-9]+$"
    val pswExp: String = "^[A-Za-z0-9]+$"
  }

  object ControllerMsg {
    val login: String = "login"
    val register: String = "register"
  }

  object AuthenticationWindow {
    val path: String = "fxml/authentication_view.fxml"
    val title: String = "Creature Mania Legends"
    val width: Int = SMALL_WIDTH
    val height: Int = SMALL_HEIGHT
  }

  object VillageWindow {
    val path: String = "fxml/my_village_view.fxml"
    val title: String = "Village"
    val width: Int = NORMAL_WIDTH
    val height: Int = NORMAL_HEIGHT
  }

  object BattleWindow {
    val path: String = "fxml/battle_view.fxml"
    val title: String = "Battle"
    val width: Int = NORMAL_WIDTH
    val height: Int = NORMAL_HEIGHT
  }

  object ArenaWindow {
    val path: String = "fxml/arena_view.fxml"
    val title: String = "Arena"
    val width: Int = NORMAL_WIDTH
    val height: Int = NORMAL_HEIGHT
  }

}
