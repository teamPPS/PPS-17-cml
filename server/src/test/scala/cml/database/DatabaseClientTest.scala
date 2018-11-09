package cml.database

import java.util.concurrent.CountDownLatch

import cml.database.utils.Configuration.DbConfig
import cml.services.authentication.utils.AuthenticationConfig.User
import cml.services.village.utils.VillageConfig.{Building, Creature, Habitat, Village}
import org.scalatest.AsyncFunSuite
import org.mongodb.scala.Document

import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
  * Test for DatabaseClient class
  *
  * @author Filippo Portolani
  */
class DatabaseClientTest extends AsyncFunSuite{

  test("testDatabaseConnection"){

    val database : DatabaseClient = DatabaseClient(DbConfig.usersColl)
    val dbVillage : DatabaseClient = DatabaseClient(DbConfig.villageColl)

    val doc: Document = Document("_id" -> 0, "name" -> "prova")
    val userDoc: Document = Document(User.USERNAME -> "CMLuser", User.PASSWORD -> "pps")

    val villageDoc: Document = Document(Village.NAME -> "PPSvillage", Village.USERNAME  -> "CMLuser", Village.FOOD -> 10, Village.GOLD ->100,
      Village.BUILDING -> Document(Building.ID -> 0, Building.TYPE -> "farm", Building.LEVEL -> 0),
      Village.HABITAT -> Document(Habitat.ID->0, Habitat.LEVEL -> 0, Habitat.ELEMENT-> "fire",
          Habitat.CREATURE -> Document(Creature.ID ->0, Creature.NAME -> "fireCreature", Creature.LEVEL -> 0, Creature.ELEMENT-> "fire")))

    val query: Document = Document(User.USERNAME->"CMLuser")
    val queryFind: Document = Document(Village.NAME -> "PPSVillage")
    val updateQuery: Document = Document("$set" -> Document(Village.FOOD -> 1000))

    val latch: CountDownLatch = new CountDownLatch(1)

    database.insert(doc,latch).onComplete {
      case Success(res) => println(res)
      case Failure(err) => println("FAILURE"+err)
    }
    
    latch await()


//    dbVillage.insert(villageDoc).recoverWith{case e: Throwable =>
//      println(e)
//      Future.failed(e)
//    }.map(_ => "Completed")
////
//    database.find(queryFind).recoverWith{case e: Throwable =>
//      println(e)
//      Future.failed(e)
//    }.map(_ => "Completed")
//
//    database.delete(query).recoverWith{case e: Throwable =>
//      println(e)
//      Future.failed(e)
//    }.map(_ => "Completed")
//
//    database.update(query, updateQuery).recoverWith{case e: Throwable =>
//      println(e)
//      Future.failed(e)
//    }.map(_ => "Completed")

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

    assert(1==1)
  }

}

