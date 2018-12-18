package cml.controller.fx

import akka.actor.ActorSelection
import cml.controller.actor.utils.ActorUtils.ActorSystemInfo.system
import cml.controller.messages.VillageRequest.{SetUpdateVillage, UpdateVillage}
import cml.model.base.{Structure, VillageMap}
import cml.model.dynamic_model.{RetrieveResource, StructureUpgrade}
import cml.model.static_model.{StaticCreatures, StaticStructure}
import cml.utils.ModelConfig.ModelClass.{CAVE_CLASS, FARM_CLASS, HABITAT_CLASS}
import cml.utils.{FoodJson, MoneyJson}
import cml.view.Tile
import play.api.libs.json.JsValue

object HandlerLogic {

  val VillageActorPath: String= "/user/VillageActor"
  val villageActor: ActorSelection = system actorSelection VillageActorPath

  private def setUpdateRemoteVillage(update: JsValue): Unit = {
    Thread.sleep(500)
    villageActor ! SetUpdateVillage(update)
  }

  private def updateRemoteVillage(update: JsValue): Unit = {
    villageActor ! UpdateVillage(update)
  }

  def retrieveLocalResource(s: Structure): RetrieveResource ={
    val retrieve = RetrieveResource(s)
    villageActor ! SetUpdateVillage(retrieve resourceJson)
    retrieve
  }

  def updateVillage(tile: Tile, x: Int, y: Int): Unit = {
    val structure = StaticStructure(tile, x, y)
    val json = structure.json
    updateRemoteVillage(json)
    VillageMap.instance().get.villageStructure += structure.getStructure
  }

  def addNewCreatureToVillage(s: Structure): Unit = {
    setUpdateRemoteVillage(StaticCreatures(s).json)
  }

  def upgradeBuildings(s: Structure): Unit = {
    setUpdateRemoteVillage(StructureUpgrade(s).structureJson)
  }

  def upgradeHabitat(s: Structure): Unit = {
    setUpdateRemoteVillage(StructureUpgrade(s).creatureJson.get)
  }

  def decrementAndUpdateMoney(gold: Int, price: Int): Unit = {
    val resourceJson = MoneyJson(gold - price).json
    VillageMap.instance().get.gold = gold - price
    setUpdateRemoteVillage(resourceJson)
  }

  def decrementAndUpdateFood(food: Int, price: Int): Unit = {
    val resourceJson = FoodJson(food - price).json
    VillageMap.instance().get.food = food - price
    setUpdateRemoteVillage(resourceJson)
  }

  def getClassName(s: Structure): String = {
    var name: String = ""
    s.getClass.getName match {
      case FARM_CLASS => name = "FARM"
      case CAVE_CLASS => name = "CAVE"
      case HABITAT_CLASS => name = "HABITAT - Element: " + s.habitatElement.getOrElse("without element")
    }
    name
  }
}
