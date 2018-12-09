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
  def delete(document: Document)(implicit ec: ExecutionContext): Future[Long]

  /**
    * Update a document with a given query
    * @param document choose the document
    * @param update the change we want to make on the document
    * @param ec implicit for ExecutionContext
    * @return a future
    */
  def update(document:Document, update:Document)(implicit ec: ExecutionContext): Future[Long]

//TODO doc
  def setUpdate(document:Document, update:Document)(implicit ec: ExecutionContext): Future[Long]

  /**
    * Find a requested document
    * @param document what we want to find
    * @param ec implicit for ExecutionContext
    * @return a future document, can be empty if document not found
    */
  def find(document: Document)(implicit ec: ExecutionContext):  Future[Document]

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
      val future = collection.insertOne(document).toFuture()
      future map(_ => "Insertion Completed") recoverWith{case e: Throwable => println(e); Future.failed(e.getCause)}
    }

    override def delete(document: Document)(implicit ec: ExecutionContext): Future[Long] = {
      val future = collection.deleteOne(document).toFuture()
      future map(_.getDeletedCount) recoverWith{case e: Throwable => Future.failed(e.getCause)}
    }

    override def update(document: Document, update: Document)(implicit ec: ExecutionContext): Future[Long] = {
      val future = collection.updateOne(document,Document("$addToSet"-> update)).toFuture() //TODO set
      future map(_.getMatchedCount) recoverWith{case e: Throwable => println(e); Future.failed(e.getCause)}
    }

    override def setUpdate(document: Document, update: Document)(implicit ec: ExecutionContext): Future[Long] = {
      val future = collection.updateOne(document,Document("$set"-> update)).toFuture()
      future map(_.getMatchedCount) recoverWith{case e: Throwable => println(e); Future.failed(e.getCause)}
    }

    override def find(document: Document)(implicit ec: ExecutionContext): Future[Document] = {
      collection.find(document).first().toFuture()
    }

    override def multipleInsert(documents: Array[Document])(implicit ec: ExecutionContext): Future[String] = {
      val future = collection.insertMany(documents).toFuture()
      future map(_ => "InsertionCompleted") recoverWith{case e: Throwable => println(e); Future.failed(e.getCause)}
    }

    override def multipleDelete(documents:Document)(implicit ec: ExecutionContext): Future[Unit] = {
      val future = collection.deleteMany(documents).toFuture()
      future map(_ => {}) recoverWith{case e: Throwable => println(e); Future.failed(e.getCause)}
    }

    override def multipleUpdate(document: Document, update: Document)(implicit ec: ExecutionContext): Future[Unit] = {
      val future = collection.updateMany(document,update).toFuture()
      future map(_ => {}) recoverWith{case e: Throwable => println(e); Future.failed(e)}
    }
  }
}