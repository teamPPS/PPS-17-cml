package cml.model.dynamic_model

import cml.model.base.Structure
import cml.utils.ModelConfig.ModelClass.{CAVE, FARM, HABITAT}
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
    case FARM => json = FoodJson(s.resource amount).json
    case CAVE =>  json = MoneyJson(s.resource amount).json
    case HABITAT => json = MoneyJson(s.resource amount).json
  }

  println("retrieve "+s.resource.amount)
  override def resourceJson: JsValue = json
}
