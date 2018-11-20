package cml.database

import java.util.concurrent.CountDownLatch

import cml.database.utils.Configuration.DbConfig
import cml.schema.User
import cml.services.village.utils.VillageConfig.{Building, Creature, Habitat, Village}
import org.scalatest.AsyncFunSuite
import org.mongodb.scala.Document

import scala.util.{Failure, Success}

/**
  * Test for DatabaseClient class
  *
  * @author Filippo Portolani
  */
class DatabaseClientTest extends AsyncFunSuite {

//  test("testDatabaseConnection") {
//
//    val usersCollection: DatabaseClient = DatabaseClient(DbConfig.usersColl)
//    val villageCollection: DatabaseClient = DatabaseClient(DbConfig.villageColl)
//
//    val doc: Document = Document("_id" -> 0, "name" -> "prova")
//    val userDoc: Document = Document(User.USERNAME -> "CMLuser", User.PASSWORD -> "pps")
//
//    val villageDoc: Document = Document(Village.NAME -> "PPSvillage", Village.USERNAME -> "CMLuser", Village.FOOD -> 10, Village.GOLD -> 100,
//      Village.BUILDING -> Document(Building.ID -> 0, Building.TYPE -> "farm", Building.LEVEL -> 0),
//      Village.HABITAT -> Document(Habitat.ID -> 0, Habitat.LEVEL -> 0, Habitat.ELEMENT -> "fire",
//        Habitat.CREATURE -> Document(Creature.ID -> 0, Creature.NAME -> "fireCreature", Creature.LEVEL -> 0, Creature.ELEMENT -> "fire")))
//
//    val updateQuery: Document = Document("$set" -> Document(User.USERNAME -> "PPS"))
//
//    val latch: CountDownLatch = new CountDownLatch(1)
//
//    usersCollection.insert(userDoc) onComplete {
//      case Success(result) => println("Insertion SUCCESS " + result)
//      case Failure(error) => println("Insertion FAILURE " + error)
//    }
//
//    latch countDown()
//
//    //
//    //    villageCollection.insert(villageDoc) onComplete{
//    //      case Success(result) => println("Insertion SUCCESS "+result)
//    //      case Failure(error) => println("Insertion FAILURE "+error)
//    //    }
//    //
//    //    usersCollection.find(userDoc) onComplete {
//    //      case Success(result) => println("Find SUCCESS "+result)
//    //      case Failure(error) => println("Find FAILURE "+error)
//    //    }
//    //
//    //    usersCollection.delete(userDoc) onComplete {
//    //      case Success(result) => println("Deletion SUCCESS"+result)
//    //      case Failure(error) => println("Deletion FAILURE"+error)
//    //    }
//    //
//    //    usersCollection.update(userDoc, updateQuery) onComplete {
//    //      case Success(result) => println("Update SUCCESS "+result)
//    //      case Failure(error) => println("Update FAILURE "+error)
//    //
//    //    }
//
//    latch await()
//    assert(1 == 1)
//  }
}

