package cml.services.village

import cml.database.DatabaseClient
import org.mongodb.scala.Document
import org.scalatest.{AsyncFunSuite, BeforeAndAfter}

import scala.collection.mutable.ListBuffer
import scala.concurrent.{ExecutionContext, Future}

class VillageServiceTest extends AsyncFunSuite with BeforeAndAfter {

  val UserField = "username"
  val FoodField = "food"
  val GoldField = "gold"
  val BuildingsField = "buildings"
  val BuildingType = "buildingType"

  var databaseClient: DatabaseClient = _
  var villageService: VillageService = _

  before {
    databaseClient = new MockDatabaseClient
    villageService = VillageService(databaseClient) // test with mocked db
//    villageService = VillageService() // test with original db
  }

  test("test enter village with existent username") {
    villageService
      .enterVillage("CMLuser")
      .map(result => assert(result.contains("farm")))
  }

  test("test update village") {
    villageService
      .updateVillage("CMLuser", """{"gold":"300", "buildings": {"1": "Test"}}""")//"""{"GoldField":"300", "BuildingsField": {"1": "Test"}}""")
      .map(result => assert(result, true))
  }

  test("test village creation") {
    villageService
      .createVillage("antonio")
      .map { document => assert(document.contains("Completed"))}
  }


  test("testDeleteVillageAndUser") {
    villageService
      .deleteVillageAndUser("CMLuser").map(result => assert(result, true))
  }

  class MockDatabaseClient extends DatabaseClient {

    var villagesList: ListBuffer[Document] = ListBuffer(
      Document(
        UserField -> "CMLuser",
        FoodField -> 200,
        GoldField -> 200,
        BuildingsField -> Document(
          "1" -> Document(
            BuildingType -> "farm"
          ),
          "2" -> Document(
            BuildingType -> "Cave"
          )
        )
      )
    )

    def getVillageList: ListBuffer[Document] = villagesList

    override def insert(document: Document)(implicit ec: ExecutionContext): Future[String] = Future {
      villagesList += document
      "Insertion Completed"
    }(executionContext)

    override def delete(document: Document)(implicit ec: ExecutionContext): Future[Long] = Future {
      villagesList = villagesList filter(doc => doc.get(UserField).equals(document.get(UserField))) // return deleted elements (filtered by original list)
      println(villagesList.length)
      villagesList.length.toLong
    }(executionContext)

    override def update(document: Document, update: Document)(implicit ec: ExecutionContext): Future[Long] = Future {
      println(update)
      println(update.toJson())
      if(villagesList.filter(doc => document.get(UserField).equals(doc.get(UserField))).head.nonEmpty)
        1L
      else 0
    }(executionContext)

    override def find(document: Document)(implicit ec: ExecutionContext): Future[Document] =
      Future {
        villagesList.filter(doc => doc.get(UserField).equals(document.get(UserField))).head
      }(executionContext)

    override def multipleInsert(documents: Array[Document])(implicit ec: ExecutionContext): Future[String] = ???

    override def multipleDelete(documents: Document)(implicit ec: ExecutionContext): Future[Unit] = ???

    override def multipleUpdate(query: Document, update: Document)(implicit ec: ExecutionContext): Future[Unit] = ???

    override def setUpdate(document: Document, update: Document)(implicit ec: ExecutionContext): Future[Long] = ???
  }
}
