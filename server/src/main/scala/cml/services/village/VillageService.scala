package cml.services.village

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

  def apply(): VillageService = VillageServiceImpl()

  case class VillageServiceImpl() extends VillageService {

    override def createVillage(villageId: String, villageName: String): Future[String] = ???
    // POST
    override def enterVillage(villageId: String): Future[Unit] = ???
    // PUT
    override def exitVillage(villageId: String): Future[Unit] = ???
    // PUT
  }
}