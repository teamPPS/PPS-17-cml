package cml.services.authentication

import scala.concurrent._

/**
  * Trait for authentication service
  *
  * @author Chiara Volonnino
  */

trait AuthenticationService {
  /**
    * To execute register into a system.
    * @param username username to register
    * @param password password to register
    * @return a future
    */

  def register (username: String, password: String): Future[String]

  /**
    * To execute login into a system.
    * @param username username with with which the user authenticates himself
    * @param password password decided by the user
    * @return a future completes successfully, otherwise it fails.
    */

  def login (username: String, password: String): Future[String]

  /**
    * Allow user to logout from the system.
    * @param username username with which the user authenticates himself
    * @return a future if the user logout successful
    */

  def logout (username: String) : Future[Unit]

  /**
    * To delete user from database
    * @param username username to delete
    * @param password password to delete
    * @return a future if the delete user successful
    */
  def delete (username: String): Future[Unit]

  /**
    * To connect service with database
    * @return
    */
  def connection: Future[Unit]

}

/**
  *
  */
object AuthenticationService {

  private val registerQuery =
    s"""
        INSERT INTO user
        VALUES (?, ?)
    """

  private val USERNAME_LOGIN = "username_login"
  private val PASSWORD_LOGIN = "password_login"
  private val loginQuery =
    s"""
        SELECT *
        FROM user
        WHERE $USERNAME_LOGIN = ?
        AND $PASSWORD_LOGIN = ?
    """
  // forse var ridondanti da provare, non saprei dire
  private val USERNAME_DELETE = "username_delete"
  private val deleteQuery =
    s"""
        DELETE FROM user
        WHERE $USERNAME_DELETE= ?
    """


}

case class AuthenticationServiceImpl() extends AuthenticationService {

  //bisogna fare connessione con il DB
  override def register(checkUsername: String, checkPassword: String): Future[String] = ???

  override def login(username: String, password: String): Future[String] = ???

  override def logout(username: String): Future[Unit] = ???

  override def delete(username: String): Future[Unit] = ???

  override def connection: Future[Unit] = ???
}