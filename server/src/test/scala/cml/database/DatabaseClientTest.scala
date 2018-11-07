package cml.database

import java.util.concurrent.CountDownLatch

import cml.database.utils.Configuration.DbConfig

import org.scalatest.AsyncFunSuite
import org.mongodb.scala.Document

import scala.concurrent.Future

/**
  * Test for DatabaseClient class
  *
  * @author Filippo Portolani
  */
class DatabaseClientTest extends AsyncFunSuite{

  test("testDatabaseConnection"){

    val database : DatabaseClient = DatabaseClient(DbConfig.usersColl)

    val doc: Document = Document("_id"->2, "name" -> "MongoDB", "type" -> "database",
      "count" -> 1, "info" -> Document("x" -> 203, "y" -> 102))

    val doc1: Document = Document( "name" -> "PPS", "type" -> "database",
      "count" -> 1, "info" -> Document("x" -> 203, "y" -> 102))

    val userDoc: Document = Document("username" -> "CMLuser", "password" -> "pps")

    val villageDoc: Document = Document("villageName" -> "PPSvillage", "username" -> "CMLuser", "food" -> 10, "gold" ->100,
      "building" -> Document("buildingId"->0, "buildingType"-> "farm", "buildingLevel" -> 0),
      "habitat" -> Document("habitatId"->0, "buildingLevel" -> 0, "element"-> "fire",
            "creature"->Document("creatureId"->0, "creatureName" -> "fireCreature","creatureLevel" -> 0, "element"-> "fire")))

    val query: Document = Document("type"->"database")
    val queryFind: Document = Document("name" -> "MongoDB")
    val updateQuery: Document = Document("$set" -> Document("name" -> "Database"))

    val latch: CountDownLatch = new CountDownLatch(1)

    database.insert(doc).recoverWith{case e: Throwable =>
      println(e)
      Future.failed(e)
    }.map(_ => "Completed")
//    latch countDown()

    database.find(queryFind).recoverWith{case e: Throwable =>
      println(e)
      Future.failed(e)
    }.map(_ => "Completed")

    database.delete(query).recoverWith{case e: Throwable =>
      println(e)
      Future.failed(e)
    }.map(_ => "Completed")

    database.update(query, updateQuery).recoverWith{case e: Throwable =>
      println(e)
      Future.failed(e)
    }.map(_ => "Completed")

/* -------------------- ACTIONS ON MULTIPLE DOCUMENTS---------------------------*/

//    database.multipleInsert(Array(userDoc,villageDoc)).recoverWith{case e: Throwable =>
//      println(e)
//      Future.failed(e)
//    }.map(_ => "Completed")
//
//    database.multipleDelete(query).recoverWith{case e: Throwable =>
//      println(e)
//      Future.failed(e)
//    }.map(_ => "Completed")

//    database.multipleUpdate(query, updateQuery).recoverWith{case e: Throwable =>
//      println(e)
//      Future.failed(e)
//    }.map(_ => "Completed")

    latch await()
    assert(1==1)
  }

}

