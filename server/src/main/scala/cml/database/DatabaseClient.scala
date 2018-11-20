package cml.database

import cml.database.utils.Configuration.DbConfig
import cml.database.utils.Configuration.DocumentNotFoundException.DocumentNotFoundException
import org.mongodb.scala._
import org.mongodb.scala.ObservableImplicits

import scala.concurrent.{ExecutionContext, Future, Promise}


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
  def find(document: Document)(implicit ec: ExecutionContext):  Future[FindObservable[Document]]

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
      future map(_ => "Insertion Completed") recoverWith{case e: Throwable => Future.failed(e.getCause)}
    }

    override def delete(document: Document)(implicit ec: ExecutionContext): Future[Unit] = {
      val future = collection.deleteOne(document).toFuture()
      future map(_ => {}) recoverWith{case e: Throwable => Future.failed(e.getCause)}
    }

    override def update(document: Document, update: Document)(implicit ec: ExecutionContext): Future[Unit] = {
      val future = collection.updateOne(document,update).toFuture()
      future map(_ => {}) recoverWith{case e: Throwable => Future.failed(e.getCause)}
    }

    override def find(document: Document)(implicit ec: ExecutionContext): Future[FindObservable[Document]] = {
      val promise: Promise[FindObservable[Document]] = Promise()
      collection.countDocuments(document).subscribe(new Observer[Long] {
        override def onNext(result: Long): Unit = result match {
          case _ => promise.success(collection.find(document))
          case 0 => onError(new Throwable(DocumentNotFoundException))
        }
        override def onError(error: Throwable): Unit = promise.failure(error)
        override def onComplete(): Unit = println("Find request completed")
      })
      promise.future
    }

    override def multipleInsert(documents: Array[Document])(implicit ec: ExecutionContext): Future[String] = {
      val future = collection.insertMany(documents).toFuture()
      future map(_ => "InsertionCompleted") recoverWith{case e: Throwable => Future.failed(e.getCause)}
    }

    override def multipleDelete(documents:Document)(implicit ec: ExecutionContext): Future[Unit] = {
      val future = collection.deleteMany(documents).toFuture()
      future map(_ => {}) recoverWith{case e: Throwable => Future.failed(e.getCause)}
    }

    override def multipleUpdate(document: Document, update: Document)(implicit ec: ExecutionContext): Future[Unit] = {
      val future = collection.updateMany(document,update).toFuture()
      future map(_ => {}) recoverWith{case e: Throwable => Future.failed(e)}
    }
  }
}