package cml.services.village

import cml.database.DatabaseClient
import cml.database.utils.Configuration.DbConfig
import cml.services.village.utils.VillageConfig.{Building, Creature, Habitat, Village}
import org.mongodb.scala.Document

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
