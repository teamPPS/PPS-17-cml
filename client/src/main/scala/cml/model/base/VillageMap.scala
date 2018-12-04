package cml.model.base

import scala.collection.mutable

/**
  * Case class representing the current state of the user's village
  * @author ecavina, Monica Gondolini
  * @param villageStructure
  * @param gold
  * @param food
  */
case class VillageMap (villageStructure: mutable.MutableList[Structure], var gold: Int, var food: Int)

object VillageMap {

  private var _instance : Option[VillageMap] = Option.empty

  def initVillage(structures: mutable.MutableList[Structure], gold: Int, food: Int): Unit = {
    _instance = Option(VillageMap(structures, gold, food))
  }

  def instance(): Option[VillageMap] = {
    if (_instance.isEmpty)
      initVillage(mutable.MutableList[Structure](), 0, 0)
    _instance
  }
}
