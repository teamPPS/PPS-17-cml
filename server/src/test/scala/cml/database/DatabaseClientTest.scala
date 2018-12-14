package cml.database

import java.util.concurrent.CountDownLatch

import cml.database.utils.Configuration.DbConfig
import cml.schema.User.{USERNAME, PASSWORD}
import cml.schema.Village._
import org.scalatest.{AsyncFunSuite, BeforeAndAfter, BeforeAndAfterAll}
import org.mongodb.scala.Document

/**
  * Test for DatabaseClient class
  *
  * @author Filippo Portolani, ecavina
  */
class DatabaseClientTest extends AsyncFunSuite with BeforeAndAfter {

  var userCollection: DatabaseClient = _
  var villageCollection: DatabaseClient = _
  var latch: CountDownLatch = _
  val username = "CMLUser"
  val password = "cml"
  val newBuildingPosition = "Position(200,200)"

  val userDoc: Document = Document(
    USERNAME -> username,
    PASSWORD -> password
  )

  val userDocID: Document = Document(
    USERNAME -> username
  )

  val villageDoc: Document = Document(
    VILLAGE_NAME_FIELD -> StringBuilder.newBuilder.append(username).append("'s village").toString(),
    USERNAME -> username,
    FOOD_FIELD -> 100,
    GOLD_FIELD -> 100
  )

  val updatedVillageDoc: Document = Document(
    VILLAGE_NAME_FIELD -> StringBuilder.newBuilder.append(username).append("'s village").toString(),
    USERNAME -> username,
    FOOD_FIELD -> 10,
    GOLD_FIELD -> 10
  )

  val newVillageElement: Document = Document(
    newBuildingPosition -> Document(
      BUILDING_TYPE_FIELD -> "Farm",
      BUILDING_LEVEL_FIELD -> 1
    )
  )

  before {
    userCollection = DatabaseClient(DbConfig.usersColl)
    villageCollection = DatabaseClient(DbConfig.villageColl)
  }

  test("testing add new user with village to database and then clean") {
    for {
      res <- userCollection.delete(userDoc)
      res1 <- userCollection.insert(userDoc)
      res2 <- villageCollection.insert(villageDoc)
      res3 <- userCollection.delete(userDoc)
      res4 <- villageCollection.delete(userDocID)
    } yield assert(
      res1.equals("Insertion Completed") &
      res2.equals("Insertion Completed") &
      res3 > 0 &
      res4 > 0
    )
  }

  test("testing update existent village and then clean") {
    for {
      res1 <- villageCollection.insert(villageDoc)
      res2 <- villageCollection.setUpdate(villageDoc, updatedVillageDoc)
      res3 <- villageCollection.find(userDocID)
      res4 <- villageCollection.delete(userDocID)
    } yield assert(
      res3.toJson().contains("10")
    )
  }

  test("testing add new element to existent village") {
    for {
      res1 <- villageCollection.insert(villageDoc)
      res2 <- villageCollection.update(villageDoc, newVillageElement)
      res3 <- villageCollection.find(userDocID)
      res4 <- villageCollection.delete(userDocID)
    } yield assert(
      res3.toJson().contains(newBuildingPosition)
    )
  }
}

