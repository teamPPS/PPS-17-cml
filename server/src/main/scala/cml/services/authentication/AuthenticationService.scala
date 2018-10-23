package cml.services.authentication

import scala.concurrent.Future

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
    * @param username username with with which the user authenticates himself
    * @return a future if the user logout successful
    */
  def logout (username: String) : Future[Unit]

}

object AuthenticationService {

  // apply

  class AuthenticationServiceImpl extends AuthenticationService{

    override def register(username: String, password: String): Future[String] = ???
    // POST
    override def login(username: String, password: String): Future[String] = ???
    // GET
    override def logout(username: String): Future[Unit] = ???
    // PUT ?
  }
}