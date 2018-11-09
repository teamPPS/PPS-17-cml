package cml.utils
/**
  * Configuration constants of the application
  * @author Monica Gondolini
  */
object Configuration {

  object Connection{
    val port: Int = 80
    val host: String = "jsonplaceholder.typicode.com"  //"localhost"
    val requestUri: String ="/posts/42" // "/uri"
  }

  object AuthenticationMsg{
    val loginSuccess: String  = "Login succeeded!"
    val loginFailure: String  = "Login denied."
    val registerSuccess: String  = "Registration completed!"
    val registerFailure: String  = "Error, registration not completed."
  }

  object InputControl{
    val emptyFields: String = "Some fields are empty."
    val userExp: String = "^[A-Za-z0-9]+$"
    val pswExp: String = "^[A-Za-z0-9]+$"
  }

  object ControllerMsg{
    val login: String = "login"
    val register: String = "register"
  }

  object AuthenticationWindow{
    val path: String = "view/authentication_view.fxml"
    val title: String = "Creature Mania Legends"
    val width: Int = 600
    val heigth: Int = 400
  }

  object BattleWindows {
    val path: String = "fxml/BattleView.fxml"
    val title: String = "Battle window"
    val width: Int = 600
    val height: Int = 400
  }

  object ArenaWindows {
    val path: String = "fxml/ArenaView.fxml"
    val title: String = "Arena window"
    val width: Int = 600
    val height: Int = 400
  }
}
