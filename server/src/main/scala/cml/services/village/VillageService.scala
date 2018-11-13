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

object VillageService {

  val database: DatabaseClient = DatabaseClient(DbConfig.villageColl)

  def apply(): VillageService = VillageServiceImpl()

  case class VillageServiceImpl() extends VillageService {

    //Esempio Documento villaggio
   /*
    val villageDoc: Document = Document(Village.NAME -> "PPSvillage", Village.USERNAME  -> "CMLuser", Village.FOOD -> 10, Village.GOLD ->100,
      Village.BUILDING -> Document(Building.ID -> 0, Building.TYPE -> "farm", Building.LEVEL -> 0),
      Village.HABITAT -> Document(Habitat.ID->0, Habitat.LEVEL -> 0, Habitat.ELEMENT-> "fire",
        Habitat.CREATURE -> Document(Creature.ID ->0, Creature.NAME -> "fireCreature", Creature.LEVEL -> 0, Creature.ELEMENT-> "fire")))
    */

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