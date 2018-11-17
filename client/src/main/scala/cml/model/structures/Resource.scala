package cml.model.structures

import cml.utils.ModelConfig.Resource.INC_BY_10

/**
  * This trait defines common operations over resources
  * @author Monica Gondolini
  */
trait Resource{
  def inc(): Unit
}

/**
  * Implementation on resource money
  * @param value money amount
  */
case class Money(var value: Int) extends Resource {
  override def inc(): Unit = value += INC_BY_10
}

/**
  * Implementation of resource food
  * @param value food amount
  */
case class Food(var value: Int) extends Resource {
  override def inc(): Unit = value += INC_BY_10
}



