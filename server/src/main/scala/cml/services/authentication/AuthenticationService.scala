package cml.services.authentication

import cml.database.DatabaseClient
import cml.database.utils.Configuration.DbConfig
import cml.schema.User.{USERNAME, PASSWORD}
import org.mongodb.scala.{Document, ObservableImplicits}

import scala.concurrent._

/**
  * Trait for authentication service
  *
  * @author Chiara Volonnino
  * @author Monica Gondolini
  */

trait AuthenticationService {

  /**
    * To execute register into a system.
    * @param username username to register
    * @param password password to register
    * @param ec is implicit fot execution context
    * @return a future completes successfully, otherwise it fails.
    */
  def register (username: String, password: String)(implicit ec: ExecutionContext): Future[String]

  /**
    * To execute login into a system.
    * @param username username with with which the user authenticates himself
    * @param password password decided by the user
    * @param ec is implicit fot execution context
    * @return a future completes successfully, otherwise it fails.
    */

  def login (username: String, password: String)(implicit ec: ExecutionContext): Future[String]

  /**
    * Allow user to logout from the system.
    * @param username username with which the user authenticates himself
    * @param ec is implicit fot execution context
    * @return a future if the user logout successful
    */
  def logout (username: String)(implicit ec: ExecutionContext): Future[Unit]

  /**
    * Check username and then valid a token
    * @param username to check
    * @param ec is implicit fot execution context
    * @return a future if username found
    */
  def validationToken(username: String)(implicit ec: ExecutionContext): Future[Unit]

}

/**
  * This object allows you to create a SQLQueries
  */
object AuthenticationService {

  val collection: DatabaseClient = DatabaseClient(DbConfig.usersColl)

  def apply(): AuthenticationService = AuthenticationServiceImpl()

  case class AuthenticationServiceImpl() extends AuthenticationService with ObservableImplicits {
    var document: Document = _

    override def register(username: String, password: String)(implicit ec: ExecutionContext): Future[String] ={
      document = Document(USERNAME->username, PASSWORD->password)
      println(document)
      collection.insert(document).map(_ => "Insertion Completed")
        .recoverWith{case e: Throwable =>
          println(e)
          Future.failed(e)
        }
    }

    override def login(username: String, password: String)(implicit ec: ExecutionContext): Future[String] =  {
      document = Document(USERNAME->username, PASSWORD->password)
      println(document)
      collection.find(document).map(_ => "Find Completed")
        .recoverWith{case e: Throwable =>
          println(e)
          Future.failed(e)
        }
    }

    override def logout(username: String)(implicit ec: ExecutionContext): Future[Unit] = {
      document = Document(USERNAME->username)
      collection.delete(document).map(_ => {})
        .recoverWith{case e: Throwable =>
          println(e)
          Future.failed(e)
        }
    }

    override def validationToken(username: String)(implicit ec: ExecutionContext): Future[Unit] = {
      document = Document(USERNAME->username)
      collection.find(document).map(_ => {})
        .recoverWith{case e: Throwable =>
          println(e)
          Future.failed(e)
        }
    }
  }
}

