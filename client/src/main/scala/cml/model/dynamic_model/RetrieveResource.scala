package cml.model.dynamic_model

import cml.model.base.{Structure, VillageMap}
import cml.utils.ModelConfig.ModelClass.{CAVE_CLASS, FARM_CLASS, HABITAT_CLASS}
import cml.utils.ModelConfig.Resource.{FOOD, MONEY}
import cml.utils.{FoodJson, MoneyJson}
import play.api.libs.json.JsValue


/**
  * @author Monica Gondolini
  */
trait Retrieve {
  def resourceJson: JsValue
  def resourceType: String
}

case class RetrieveResource(s: Structure) extends Retrieve {

  private var json: JsValue = _
  private var resType: String = _
  private val village = VillageMap.instance().get

  s.getClass.getName match {
    case FARM_CLASS =>
      village.food += s.resource.amount
      json = FoodJson(village.food).json
      resType = FOOD
    case CAVE_CLASS =>
      village.gold += s.resource.amount
      json = MoneyJson(village.gold).json
      resType = MONEY
    case HABITAT_CLASS =>
      village.gold += s.resource.amount
      json = MoneyJson(village.gold).json
      resType = MONEY
  }

  s.resource.take()

  override def resourceType: String = resType
  override def resourceJson: JsValue = json
}
