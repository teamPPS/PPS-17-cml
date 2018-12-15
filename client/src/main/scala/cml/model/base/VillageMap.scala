package cml.model.base

import scala.collection.mutable

trait VillageMap[A] {

  def initVillage(structures: mutable.MutableList[Structure], gold: Int, food: Int, villageName: String): Unit
  def createVillage[A](howToPopulate: A => Unit)(villageInfo: A): Unit
  def instance(): Option[ConcreteVillageMap]
}

/**
  * Case class representing the current state of the user's village
  * @author ecavina, Monica Gondolini
  * @param villageStructure list of structures
  * @param gold village's gold
  * @param food village's foog
  * @param villageName owner of the village
  */
case class ConcreteVillageMap (villageStructure: mutable.MutableList[Structure], var gold: Int, var food: Int, var villageName: String)

object VillageMap {

  private var _instance : Option[ConcreteVillageMap] = Option.empty

  def initVillage(structures: mutable.MutableList[Structure], gold: Int, food: Int, villageName: String): Unit = {
    _instance = Option(VillageMap(structures, gold, food, villageName))
  }

  def createVillage[A](howToPopulate: A => Unit)(villageInfo: A): Unit = howToPopulate(villageInfo)

  def instance(): Option[ConcreteVillageMap] = {
    if (_instance.isEmpty)
      initVillage(mutable.MutableList[Structure](), 0, 0, "default")
    _instance
  }

}