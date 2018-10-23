package cml.services.village

import java.util.Optional

/**
  * Trail for village service
  *
  * @author Chiara Volonnino
  */
trait VillageService {

  def createVillage(villageIdentifier: String, villageName: String) : Optional[String]

  def enterVillage(villageIdentifier: String) : Optional[Unit]

  def exitVillage(villageIdentifier: String) : Optional[Unit]
}

object VillageService {

  class VillageServiceImpl extends VillageService {

    override def createVillage(villageId: String, villageName: String): Optional[String] = ???
    // POST
    override def enterVillage(villageId: String): Optional[Unit] = ???
    // PUT
    override def exitVillage(villageId: String): Optional[Unit] = ???
    // PUT
  }
}