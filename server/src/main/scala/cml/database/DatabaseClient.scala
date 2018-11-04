package cml.database

/**
  * Connector to the MongoDB database
  *
  * @author Filippo Portolani
  */

import org.mongodb.scala._
import java.util.concurrent.CountDownLatch

case class DatabaseClient(uri:String, db:String, coll:String) {
  import DatabaseClient._

  val mongoClient: MongoClient = MongoClient(uri)
  val database: MongoDatabase = mongoClient getDatabase (db)
  val collection: MongoCollection[Document] = database getCollection (coll)

  def document:Document = createDoc()
  //insertOne(collection,document)
  findFirst(collection)
}

object DatabaseClient {

  def createDoc():Document = {
    val doc: Document = Document("_id" -> 1, "name" -> "MongoDB", "type" -> "database",
      "count" -> 1, "info" -> Document("x" -> 203, "y" -> 103))

    doc
  }
  def insertOne(collection: MongoCollection[Document], document: Document): Unit = {
    val latch = new CountDownLatch(1)
    val observable: Observable[Completed] = collection insertOne(document)
    observable.subscribe(new Observer[Completed]{
      override def onNext(result: Completed): Unit = {
        println("Inserted")
        latch countDown()
      }
      override def onError(e: Throwable): Unit = println("Failed")
      override def onComplete(): Unit = {println("Completed")
      }
    })
    latch await()
  }

  def findFirst(collection: MongoCollection[Document]): Unit ={


  }

}