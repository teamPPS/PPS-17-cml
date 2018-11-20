package cml.model.base

import cml.utils.ModelConfig.Resource.{INC_BY_10, INIT_VALUE}

/**
  * This trait defines common operations over resources
  * @author Monica Gondolini
  */
trait Resource{

  /**
    * Resource increment
    */
  def inc(level: Int): Unit

  /**
    * Retrieve the resource
    * @return the amount of resources
    */
  def take(): Int
}

/**
  * Implementation on resource money
  * @param amount money amount
  */
case class Money(var amount: Int) extends Resource {
  override def inc(level: Int): Unit = {
    amount += (level*INC_BY_10)
  }
  override def take(): Int = {
    val taken = amount
    amount = INIT_VALUE
    taken
  }
}

/**
  * Implementation of resource food
  * @param amount food amount
  */
case class Food(var amount: Int) extends Resource {
  override def inc(level: Int): Unit ={
    amount += (level*INC_BY_10)
  }
  override def take(): Int = {
    val taken = amount
    amount = INIT_VALUE
    taken
  }
}




