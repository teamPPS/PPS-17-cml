package cml.model.base

import scala.collection.mutable

/**
  * Case class representing the current state of the user's village
  * @author ecavina, Monica Gondolini
  * @param villageStructure list of structures componing the village
  * @param gold amount of user's gold resource
  * @param food amount of user's food resource
  * @param username user's username
  */
case class VillageMap (villageStructure: mutable.MutableList[Structure], var gold: Int, var food: Int, var villageName: String)

object VillageMap {

  private var _instance : Option[VillageMap] = Option.empty

  def initVillage(structures: mutable.MutableList[Structure], gold: Int, food: Int, villageName: String): Unit = {
    _instance = Option(VillageMap(structures, gold, food, villageName))
  }

  def createVillage[A](howToPopulate: A => Unit)(villageInfo: A): Unit = howToPopulate(villageInfo)

  def instance(): Option[VillageMap] = {
    if (_instance.isEmpty)
      initVillage(mutable.MutableList[Structure](), 0, 0, "default")
    _instance
  }

}