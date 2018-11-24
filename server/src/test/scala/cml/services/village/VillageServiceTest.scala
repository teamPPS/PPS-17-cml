package cml.services.village

import cml.database.DatabaseClient
import org.mongodb.scala.{Document, FindObservable}
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.{ExecutionContext, Future}

class VillageServiceTest extends FunSuite with BeforeAndAfter {

  var databaseClient: DatabaseClient = _
  var villageService: VillageService = _

  before {
    databaseClient = new MockDatabaseClient
    villageService = VillageService(databaseClient)
  }

  test("testEnterVillage") {
    villageService
  }

  test("testUpdateVillage") {

  }

  test("testCreateVillage") {

  }

  test("testDeleteVillageAndUser") {

  }

}

class MockDatabaseClient extends DatabaseClient {

  override def insert(document: Document)(implicit ec: ExecutionContext): Future[String] = ???

  override def delete(document: Document)(implicit ec: ExecutionContext): Future[Unit] = ???

  override def update(document: Document, update: Document)(implicit ec: ExecutionContext): Future[Unit] = ???

  override def find(document: Document)(implicit ec: ExecutionContext): Future[FindObservable[Document]] = ???

  override def multipleInsert(documents: Array[Document])(implicit ec: ExecutionContext): Future[String] = ???

  override def multipleDelete(documents: Document)(implicit ec: ExecutionContext): Future[Unit] = ???

  override def multipleUpdate(query: Document, update: Document)(implicit ec: ExecutionContext): Future[Unit] = ???
}