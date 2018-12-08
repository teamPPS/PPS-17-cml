package cml.services.village

import cml.database.DatabaseClient
import cml.database.utils.Configuration.DbConfig
import cml.schema.User._
import cml.schema.Village._
import org.mongodb.scala.Document
import org.mongodb.scala.bson.BsonArray
import play.api.libs.json.Json

import scala.concurrent._

/**
  * Village service
  *
  * @author ecavina
  */
sealed trait VillageService {

  /**
    * Insert a new Document with base resources value, username and village name
    * and get created Document in JSON
    * @param username using to create a specific village for this user
    * @return a future string representing the new village in JSON
    */
  def createVillage(username: String)(implicit ec: ExecutionContext): Future[String]

  /**
    * Get a future string with user's village in JSON
    * @param username using to find personal village
    * @return a future string representing the village in jSON
    */
  def enterVillage(username: String)(implicit ec: ExecutionContext): Future[String]

  /**
    * Update a user's village with updated information
    * @param username using to find personal village
    * @param update a json describing what to update
    * @return successful or failed update
    */
  def updateVillage(username: String, update: String)(implicit ec: ExecutionContext): Future[Boolean]

  /**
    * Delete user's village and account
    * @param username target village to delete
    * @return delete successful
    */
  def deleteVillageAndUser(username: String)(implicit ec: ExecutionContext): Future[Boolean]
}

object VillageService {

  def apply(databaseClient: DatabaseClient = DatabaseClient(DbConfig.villageColl)): VillageService =
    VillageServiceImpl(databaseClient)

  case class VillageServiceImpl(villageCollection: DatabaseClient) extends VillageService {
    var document: Document = _

    override def createVillage(username: String)(implicit ec: ExecutionContext): Future[String] = {

      val initialBuilding  = Document(
        SINGLE_BUILDING_FIELD -> Document(
          BUILDING_TYPE_FIELD -> "CAVE",
          BUILDING_LEVEL_FIELD -> 1,
          BUILDING_POSITION_FIELD -> Document(
            "x" -> 1,
            "y" -> 1
          )
        )
      )

      println("initialBuilding.toString())"+initialBuilding.toString())

      document = Document(
        VILLAGE_NAME_FIELD -> StringBuilder.newBuilder.append(username).append("'s village").toString(),
        USERNAME -> username,
        FOOD_FIELD -> 100,
        GOLD_FIELD -> 100,
        MULTIPLE_BUILDINGS_FIELD -> List(initialBuilding),
        MULTIPLE_HABITAT_FIELD -> List()
      )
      villageCollection.insert(document).map(_ => "Completed")
        .recoverWith{case e: Throwable =>
          println(e)
          Future.failed(e)
        }
    }

    override def enterVillage(username: String)(implicit ec: ExecutionContext): Future[String] = {
      document = Document(USERNAME -> username)
      villageCollection.find(document)
        .map(doc => if(doc.isEmpty) "Village not found" else doc.toJson())
        .recoverWith{case e: Throwable =>
          println(e)
          Future.failed(e)
        }
    }

    override def updateVillage(username: String, update: String)(implicit ec: ExecutionContext): Future[Boolean] = {
      val queryDocument = Document(USERNAME -> username)
      villageCollection.update(queryDocument, Document(Json.parse(update).toString()))
        .map(modifiedDocument => modifiedDocument>0)
        .recoverWith{case e: Throwable =>
          println(e)
          Future.failed(e)
        }
    }

    override def deleteVillageAndUser(username: String)(implicit ec: ExecutionContext): Future[Boolean] = {
      document = Document(USERNAME -> username)
      villageCollection.delete(document)
        .map(deletedDocument => deletedDocument>0)
        .recoverWith{case e: Throwable =>
          println(e)
          Future.failed(e)
        }
    }
  }
}