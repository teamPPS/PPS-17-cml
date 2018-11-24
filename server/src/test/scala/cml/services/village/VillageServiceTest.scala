package cml.services.village

import cml.database.DatabaseClient
import org.mongodb.scala.{Document, FindObservable}
import org.scalatest.{AsyncFunSuite, BeforeAndAfter}

import scala.concurrent.{ExecutionContext, Future}

class VillageServiceTest extends AsyncFunSuite with BeforeAndAfter {

  var databaseClient: DatabaseClient = _
  var villageService: VillageService = _

  before {
    databaseClient = new MockDatabaseClient
    villageService = VillageService(databaseClient)
  }

  test("testEnterVillage") {
    villageService
      .enterVillage("user1")
      .map(result => assert(result.contains("Farm")))
  }

  test("testUpdateVillage") {
    villageService
      .updateVillage("user1", "")
      .map(result => assert(result, true))
  }

  test("testCreateVillage") {
    villageService
      .createVillage("antonio")
      .map { document => assert(document.contains("antonio"))}
  }

  test("testDeleteVillageAndUser") {
    villageService
      .deleteVillageAndUser("user1").map(result => assert(result, true))
  }

  class MockDatabaseClient extends DatabaseClient {

    val villageList: List[Document] = List(
      Document(
        "username" -> "user1",
        "food" -> 200,
        "gold" -> 200,
        "buildings" -> Document(
          "1" -> Document(
            "buildingsType" -> "Farm"
          ),
          "2" -> Document(
            "buildingsType" -> "Cave"
          )
        )
      )
    )

    def getVillageList: List[Document] = villageList

    override def insert(document: Document)(implicit ec: ExecutionContext): Future[String] = Future { document.toJson() }(executionContext)

    override def delete(document: Document)(implicit ec: ExecutionContext): Future[Unit] = Future {  }(executionContext)

    override def update(document: Document, update: Document)(implicit ec: ExecutionContext): Future[Unit] = Future {  }(executionContext)

    override def find(document: Document)(implicit ec: ExecutionContext): Future[FindObservable[Document]] = ???

    override def multipleInsert(documents: Array[Document])(implicit ec: ExecutionContext): Future[String] = ???

    override def multipleDelete(documents: Document)(implicit ec: ExecutionContext): Future[Unit] = ???

    override def multipleUpdate(query: Document, update: Document)(implicit ec: ExecutionContext): Future[Unit] = ???
  }
}
