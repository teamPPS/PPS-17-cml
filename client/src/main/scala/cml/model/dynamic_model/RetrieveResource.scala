package cml.model.dynamic_model

import cml.model.base.Structure
import cml.utils.ModelConfig.ModelClass.{CAVE_CLASS, FARM_CLASS, HABITAT_CLASS}
import cml.utils.{FoodJson, MoneyJson}
import play.api.libs.json.JsValue


/**
  * @author Monica Gondolini
  */
trait Retrieve {
  def resourceJson: JsValue
}

case class RetrieveResource(s: Structure) extends Retrieve {

  private var json: JsValue = _

  s.resource.take()
  s.getClass.getName match {
    case FARM_CLASS => json = FoodJson(s.resource amount).json
    case CAVE_CLASS => json = MoneyJson(s.resource amount).json
    case HABITAT_CLASS => json = MoneyJson(s.resource amount).json
  }

  override def resourceJson: JsValue = json
}
