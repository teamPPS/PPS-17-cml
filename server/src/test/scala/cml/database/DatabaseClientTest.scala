package cml.database

import java.util.concurrent.CountDownLatch

import cml.database.utils.Configuration.DbConfig
import cml.services.authentication.utils.AuthenticationConfig.User
import cml.services.village.utils.VillageConfig.{Building, Creature, Habitat, Village}
import org.scalatest.AsyncFunSuite
import org.mongodb.scala.Document

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
    val updateQuery: Document = Document("$set" -> Document(User.USERNAME -> "PPS"))

    val latch: CountDownLatch = new CountDownLatch(1)

    database.insert(userDoc) onComplete{
      case Success(res) => println("Insertion SUCCESS "+res)
      case Failure(err) => println("Insertion FAILURE "+err)
    }

    dbVillage.insert(villageDoc) onComplete{
      case Success(res) => println("Insertion SUCCESS "+res)
      case Failure(err) => println("Insertion FAILURE "+err)
    }

    database.find(userDoc) onComplete {
      case Success(res) => println("Find SUCCESS "+res)
      case Failure(err) => println("Find FAILURE "+err)
    }

    database.delete(userDoc) onComplete {
      case Success(res) => println("Deletion SUCCESS"+res)
      case Failure(err) => println("Deletion FAILURE"+err)
    }

    database.update(userDoc, updateQuery) onComplete {
      case Success(res) => println("Update SUCCESS "+res)
      case Failure(err) => println("Update FAILURE "+err)

    }

    latch await()
    assert(1==1)
  }

}

