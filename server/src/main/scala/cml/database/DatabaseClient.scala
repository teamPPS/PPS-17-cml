package cml.database

import cml.database.utils.Configuration.DbConfig
import org.mongodb.scala._
import org.mongodb.scala.ObservableImplicits

import scala.concurrent.{ExecutionContext, Future}


/**
  * Connector to the MongoDB database
  *
  * @author Filippo Portolani, Monica Gondolini
  */

trait DatabaseClient{

  /**
    * Inserts a new document in the Collection
    * @param document to be inserted
    * @param ec implicit for ExecutionContext
    * @return a future
    */
  def insert(document: Document)(implicit ec: ExecutionContext): Future[String]

  /**
    * Delete a specific document from the database
    * @param document select the document we want to delete
    * @param ec implicit for ExecutionContext
    * @return a future
    */
  def delete(document: Document)(implicit ec: ExecutionContext): Future[Unit]

  /**
    * Update a document with a given query
    * @param document choose the document
    * @param update the change we want to make on the document
    * @param ec implicit for ExecutionContext
    * @return a future
    */
  def update(document:Document, update:Document)(implicit ec: ExecutionContext): Future[Unit]

  /**
    * Find a requested document
    * @param document what we want to find
    * @param ec implicit for ExecutionContext
    * @return a future
    */
  def find(document: Document)(implicit ec: ExecutionContext): Future[Unit]

  /**
    * Allow to insert multiple documents in the database
    * @param documents to insert
    * @param ec implicit for ExecutionContext
    * @return a future
    */

  def multipleInsert(documents: Array[Document])(implicit ec: ExecutionContext): Future[String]

  /**
    * Delete multiple documents
    * @param documents to delete
    * @param ec implicit for ExecutionContext
    * @return a future
    */
  def multipleDelete(documents: Document)(implicit ec: ExecutionContext): Future[Unit]

  /**
    * Update multiple documents
    * @param query choose the document
    * @param update the change we want to make on the document
    * @param ec implicit for ExecutionContext
    * @return a future
    */
  def multipleUpdate(query:Document, update:Document)(implicit ec: ExecutionContext): Future[Unit]
}

/**
  * Companion Object
  */
object DatabaseClient {
  val mongoClient: MongoClient = MongoClient(DbConfig.uri)
  val database: MongoDatabase = mongoClient getDatabase DbConfig.dbName

  def apply(coll: String): DatabaseClient = DatabaseClientImpl(coll: String)

  case class DatabaseClientImpl(coll: String) extends DatabaseClient with ObservableImplicits {
    val collection: MongoCollection[Document] = database getCollection coll

    override def insert(document: Document)(implicit ec: ExecutionContext): Future[String] = {
      collection.insertOne(document).toFuture()
        .map(result => result.toString)
        .recoverWith{
          case e =>
            println("error")
            Future.failed(e)
        }

    }

    override def delete(document: Document)(implicit ec: ExecutionContext): Future[Unit] = {
      collection.deleteOne(document).toFuture()
        .recoverWith{case e: Throwable =>
        println(e)
        Future.failed(e)
      }.map(result => result.toString)
    }

    override def update(document: Document, update: Document)(implicit ec: ExecutionContext): Future[Unit] = {
      collection.updateOne(document,update).toFuture()
        .recoverWith{case e: Throwable =>
          println(e)
          Future.failed(e)
        }.map(result => result.toString)
    }

    override def find(document: Document)(implicit ec: ExecutionContext): Future[Unit] = {
      collection.find(document).toFuture()
        .recoverWith{case e: Throwable =>
          println(e)
          Future.failed(e)
        }.map(result => result.toString)
    }

    override def multipleInsert(documents: Array[Document])(implicit ec: ExecutionContext): Future[String] = {
      collection.insertMany(documents).toFuture()
        .recoverWith{case e: Throwable =>
          println(e)
          Future.failed(e)
        }.map(result => result.toString)
    }

    override def multipleDelete(documents:Document)(implicit ec: ExecutionContext): Future[Unit] = {
      collection.deleteMany(documents).toFuture()
        .recoverWith{case e: Throwable =>
          println(e)
          Future.failed(e)
        }.map(result => result.toString)
    }

    override def multipleUpdate(document: Document, update: Document)(implicit ec: ExecutionContext): Future[Unit] = {
      collection.updateMany(document,update).toFuture()
        .recoverWith{case e: Throwable =>
          println(e)
          Future.failed(e)
        }.map(result => result.toString)
    }
  }
}