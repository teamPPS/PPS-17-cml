package cml.services.authentication

import cml.database.DatabaseClient
import cml.database.utils.Configuration.DbConfig
import cml.services.authentication.utils.AuthenticationConfig.User
import org.mongodb.scala.{Document, ObservableImplicits}

import scala.concurrent._

/**
  * Trait for authentication service
  *
  * @author Chiara Volonnino, Monica Gondolini
  */

trait AuthenticationService {
  /**
    * To execute register into a system.
    * @param username username to register
    * @param password password to register
    * @return a future
    */

  def register (username: String, password: String)(implicit ec: ExecutionContext): Future[String]

  /**
    * To execute login into a system.
    * @param username username with with which the user authenticates himself
    * @param password password decided by the user
    * @return a future completes successfully, otherwise it fails.
    */

  def login (username: String, password: String)(implicit ec: ExecutionContext): Future[String]

  /**
    * Allow user to logout from the system.
    * @param username username with which the user authenticates himself
    * @return a future if the user logout successful
    */

  def logout (username: String)(implicit ec: ExecutionContext): Future[Unit]

  /**
    * To delete user from database
    * @param username username to delete
    * @return a future if the delete user successful
    */
  def delete (username: String)(implicit ec: ExecutionContext): Future[Unit]

  /**
    * Check username and then valid a token
    * @param username to check
    * @return a future if username found
    */
  def validationToken(username: String)(implicit ec: ExecutionContext): Future[Unit]

  /**
    * To connect service with database
    * @return
    */
  def connection: Future[Unit]

}

/**
  * This object allows you to create a SQLQueries
  */
object AuthenticationService {

  val database: DatabaseClient = DatabaseClient(DbConfig.usersColl)

  def apply(): AuthenticationService = AuthenticationServiceImpl()
  case class AuthenticationServiceImpl() extends AuthenticationService with ObservableImplicits{

    var document: Document = _

    override def register(username: String, password: String)(implicit ec: ExecutionContext): Future[String] = {
      document = Document(User.USERNAME->username, User.PASSWORD->password)
      database.insert(document).map(_ => "Completed")
    }

    override def login(username: String, password: String)(implicit ec: ExecutionContext): Future[String] = {
      document = Document(User.USERNAME->username, User.PASSWORD->password)
      database.find(document).map(_ => "Completed")
    }

    override def logout(username: String)(implicit ec: ExecutionContext): Future[Unit] = {
      document = Document(User.USERNAME->username)
      database.find(document).map(_ => "Completed")
    }

    override def delete(username: String)(implicit ec: ExecutionContext): Future[Unit] = {
      document = Document(User.USERNAME->username)
      database.delete(document).map(_ => "Completed")
    }

    override def validationToken(username: String)(implicit ec: ExecutionContext): Future[Unit] = {
      document = Document(User.USERNAME->username)
      database.find(document).map(_ => "Completed")
    }

    override def connection: Future[Unit] = ???
  }

}

