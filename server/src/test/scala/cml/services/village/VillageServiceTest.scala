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
    villageService = VillageService(databaseClient)
  }

  test("test enter village with existent username") {
    villageService
      .enterVillage("user1")
      .map(result => assert(result.contains("Farm")))
  }

  test("testUpdateVillage") {
    villageService
      .updateVillage("user1", "")
      .map(result => assert(result, true))
  }

  test("test village creation") {
    villageService
      .createVillage("antonio")
      .map { document => assert(document.contains("antonio"))}
  }

  test("testDeleteVillageAndUser") {
    villageService
      .deleteVillageAndUser("user1").map(result => assert(result, true))
  }

  class MockDatabaseClient extends DatabaseClient {


    var villagesList: ListBuffer[Document] = ListBuffer(
      Document(
        UserField -> "user1",
        FoodField -> 200,
        GoldField -> 200,
        BuildingsField -> Document(
          "1" -> Document(
            BuildingType -> "Farm"
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
      document.toJson()
    }(executionContext)

    override def delete(document: Document)(implicit ec: ExecutionContext): Future[Unit] = Future {
      villagesList = villagesList filter(doc => !doc.get(UserField).equals(document.get(UserField)))
    }(executionContext)

    override def update(document: Document, update: Document)(implicit ec: ExecutionContext): Future[Unit] = Future {

    }(executionContext)

    override def find(document: Document)(implicit ec: ExecutionContext): Future[Document] =
      Future {
        villagesList.filter(doc => doc.get(UserField).equals(document.get(UserField))).head
      }(executionContext)

    override def multipleInsert(documents: Array[Document])(implicit ec: ExecutionContext): Future[String] = ???

    override def multipleDelete(documents: Document)(implicit ec: ExecutionContext): Future[Unit] = ???

    override def multipleUpdate(query: Document, update: Document)(implicit ec: ExecutionContext): Future[Unit] = ???
  }
}
