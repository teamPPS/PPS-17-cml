package cml.schema

/**
  * This trait describe the user
  *
  * @author Chiara Volonnino
  */

trait User {
  def username: String
  def password: String
}

object User {
  val Username = "username"
  val Password = "password"
}
