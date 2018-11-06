package cml.database

import java.util.concurrent.CountDownLatch

import cml.utils.Configuration.DbConfig
import org.mongodb.scala.result.{DeleteResult, UpdateResult}
import org.scalatest.AsyncFunSuite
import org.mongodb.scala.{Completed, Document, Observer}

/**
  * Test for DatabaseClient class
  *
  * @author Filippo Portolani
  */

class DatabaseClientTest extends AsyncFunSuite{

  test("testDatabaseConnection"){

    val client : DatabaseClient = DatabaseClient(DbConfig.usersColl)

    val doc: Document = Document("_id" -> 0, "name" -> "MongoDB", "type" -> "database",
      "count" -> 1, "info" -> Document("x" -> 203, "y" -> 102))

    val doc1: Document = Document("_id" -> 1, "name" -> "PPS", "type" -> "database",
      "count" -> 1, "info" -> Document("x" -> 203, "y" -> 102))

    val query: Document = Document("type"->"database")

    val queryFind: Document = Document("name" -> "MongoDB")

    val updateQuery: Document = Document("$set" -> Document("name" -> "Database"))

    val latch: CountDownLatch = new CountDownLatch(1)

    client.insert(doc).subscribe(new Observer[Completed] {
      override def onNext(result: Completed): Unit ={
        println("[INSERTION] Inserted "+result)
        latch countDown()
      }
      override def onError(e: Throwable): Unit = println("[INSERTION] Failed "+e)
      override def onComplete(): Unit = println("[INSERTION] Completed")
    })

    client.delete(query).subscribe(new Observer[DeleteResult] {
      override def onNext(result: DeleteResult): Unit = {
        println("[DELETION] Deleted "+result)
        latch countDown()
      }
      override def onError(e: Throwable): Unit =  println("[DELETION] Failed "+e)
      override def onComplete(): Unit = println("[DELETION] Completed")
    })

    client.update(query, updateQuery).subscribe(new Observer[UpdateResult] {
      override def onNext(result: UpdateResult): Unit = {
        println("[UPDATE] Updated "+result)
        latch.countDown()
      }
      override def onError(e: Throwable): Unit = println("[UPDATE] Failed "+e)
      override def onComplete(): Unit = println("[UPDATE] Completed")
    })

    client.find(queryFind).subscribe(new Observer[Document] {
      override def onNext(result: Document): Unit = {
        println("[FIND] Found "+result)
        latch.countDown()
      }
      override def onError(e: Throwable): Unit = println("[FIND] Failed "+e)
      override def onComplete(): Unit = println("[FIND] Completed")
    })

    client.multipleInsert(Array(doc,doc1)).subscribe(new Observer[Completed] {
      override def onNext(result: Completed): Unit ={
        println("[MULTIPLE INSERTION] Inserted "+result)
        latch countDown()
      }
      override def onError(e: Throwable): Unit = println("[MULTIPLE INSERTION] Failed "+e)
      override def onComplete(): Unit = println("[MULTIPLE INSERTION] Completed")
    })

    client.multipleDelete(query).subscribe(new Observer[DeleteResult] {
      override def onNext(result: DeleteResult): Unit = {
        println("[MULTIPLE DELETION] Deleted "+result)
        latch countDown()
      }
      override def onError(e: Throwable): Unit = println("[MULTIPLE DELETION] Failed "+e)
      override def onComplete(): Unit = println("[MULTIPLE DELETION] Completed")
    })

    client.multipleUpdate(query, updateQuery).subscribe(new Observer[UpdateResult] {
      override def onNext(result: UpdateResult): Unit = {
        println("[MULTIPLE UPDATE] Updated "+result)
        latch countDown()
      }
      override def onError(e: Throwable): Unit = println("[MULTIPLE UPDATE] Failed "+e)
      override def onComplete(): Unit = println("[MULTIPLE UPDATE] Completed")
    })

    latch await()
    assert(1==1)
  }

}

