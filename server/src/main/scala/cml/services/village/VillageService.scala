package cml.services.village

import cml.database.DatabaseClient
import cml.database.utils.Configuration.DbConfig

import scala.concurrent.Future


/**
  * Village service
  *
  * @author ecavina
  */
sealed trait VillageService {

  /**
    * To execute creation of a village
    * @param villageIdentifier
    * @param villageName
    * @return a scala future
    */
  def createVillage(villageIdentifier: String, villageName: String) : Future[String]

  /**
    * To enter the village
    * @param villageIdentifier
    * @return a scala future
    */
  def enterVillage(villageIdentifier: String) : Future[Unit]

  /**
    * To exit the village
    * @param villageIdentifier
    * @return a scala future
    */
  def exitVillage(villageIdentifier: String) : Future[Unit]
}

object VillageService {

  val database: DatabaseClient = DatabaseClient(DbConfig.villageColl)

  def apply(): VillageService = VillageServiceImpl()

  case class VillageServiceImpl() extends VillageService {

    override def createVillage(villageId: String, villageName: String): Future[String] = ???
    //Creare Documento db.insert(doc)
    // POST
    override def enterVillage(villageId: String): Future[Unit] = ???
    //db.find(doc)
    // PUT
    override def exitVillage(villageId: String): Future[Unit] = ???
    //db.find(doc)
    // PUT
  }
}