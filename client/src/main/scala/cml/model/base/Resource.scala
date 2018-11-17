package cml.model.base

import cml.utils.ModelConfig.Resource.{INC_BY_10, INIT_VALUE}

/**
  * This trait defines common operations over resources
  * @author Monica Gondolini
  */
trait Resource{
  def inc(): Unit //incremento le risorse
  def take(): Int //prendo le risorse e azzero il valore
}

/**
  * Implementation on resource money
  * @param value money amount
  */
case class Money(var value: Int) extends Resource {
  override def inc(): Unit = value += INC_BY_10

  override def take(): Int = {
    val amount = value
    value = INIT_VALUE
    amount
  }
}

/**
  * Implementation of resource food
  * @param value food amount
  */
case class Food(var value: Int) extends Resource {
  override def inc(): Unit = value += INC_BY_10

  override def take(): Int = {
    val amount = value
    value = INIT_VALUE
    amount
  }
}



