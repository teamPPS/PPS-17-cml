package cml.controller.actor.utils

/**
  * Object utils to managements view messages
  */

object ViewMessage {

  object ViewAuthenticationMessage {
    val loginSuccess: String = "Login succeeded!"
    val loginFailure: String = "Login denied."
    val registerSuccess: String = "Registration completed!"
    val registerFailure: String = "Error, registration not completed."
  }

  object ViewVillageMessage {
    val createFailure: String = "Error, village creation not completed."
    val enterFailure: String = "Error, cannot enter the village."
    val updateFailure: String = "Error, update not completed."
    val deleteFailure: String = "Error, deletion not completed."
  }

}