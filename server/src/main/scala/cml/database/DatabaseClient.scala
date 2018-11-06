package cml.database

import cml.utils.Configuration.DbConfig
import org.mongodb.scala._
import org.mongodb.scala.result.{DeleteResult, UpdateResult}

/**
  * Connector to the MongoDB database
  *
  * @author Filippo Portolani
  */

trait DatabaseClient{

  /**
    * Inserts a new document in the Collection
    * @param document to be inserted
    * @return Observable
    */
  def insert(document: Document):Observable[Completed]

  /**
    * Delete a specific document from the database
    * @param query select the document we want to delete
    * @return SingleObservable
    */
  def delete(query: Document):SingleObservable[DeleteResult]

  /**
    * Update a document with a given query
    * @param query choose the document
    * @param update the change we want to make on the document
    * @return Observable
    */
  def update(query:Document, update:Document):Observable[UpdateResult]

  /**
    * Find a requested document
    * @param query what we want to find
    * @return Observable
    */
  def find(query: Document):Observable[Document]

  /**
    * Allow to insert multiple documents in the database
    * @param documents to insert
    * @return Observable
    */

  def multipleInsert(documents: Array[Document]):Observable[Completed]

  /**
    * Delete multiple documents
    * @param documents to delete
    * @return SingleObservable
    */
  def multipleDelete(documents: Document):SingleObservable[DeleteResult]

  /**
    * Update multiple documents
    * @param query choose the document
    * @param update the change we want to make on the document
    * @return Observable
    */

  def multipleUpdate(query:Document, update:Document):Observable[UpdateResult]

}

/**
  * Companion Object
  */

object DatabaseClient {
  val mongoClient: MongoClient = MongoClient(DbConfig.uri)
  val database: MongoDatabase = mongoClient getDatabase DbConfig.dbName

  def apply(coll: String): DatabaseClient = DatabaseClientImpl(coll: String)
  case class DatabaseClientImpl(coll: String) extends DatabaseClient {

    val collection: MongoCollection[Document] = database getCollection coll

    override def insert(document: Document): Observable[Completed] = {
      val observable: Observable[Completed] = collection.insertOne(document)
      observable
    }

    override def delete(query: Document): SingleObservable[DeleteResult] = {
      val observable: SingleObservable[DeleteResult] = collection.deleteOne(query)
      observable
    }

    override def update(query: Document, update: Document): Observable[UpdateResult] = {
      val observable: Observable[UpdateResult] = collection.updateOne(query, update)
      observable
    }

    override def find(query: Document): Observable[Document] = {
      val observable: Observable[Document] = collection.find(query)
      observable
    }

    override def multipleInsert(documents: Array[Document]): Observable[Completed] = {
      val observable: Observable[Completed] = collection.insertMany(documents)
      observable
    }

    override def multipleDelete(documents:Document): SingleObservable[DeleteResult] = {
      val observable: SingleObservable[DeleteResult] = collection.deleteMany(documents)
      observable
    }

    override def multipleUpdate(query: Document, update: Document): Observable[UpdateResult] = {
      val observable = collection.updateMany(query,update)
      observable
    }
  }
}