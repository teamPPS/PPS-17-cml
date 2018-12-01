package cml.model.base

import cml.utils.ModelConfig.Resource._

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
    * @return the amount taken of resources
    */
  def take(): Int

  /**
    * Decrements the resource
    */
  def dec(price: Int): Unit

  /**
    * The amount of resources
    */
  def amount: Int
}

/**
  * Implementation on resource money
  * @param moneyAmount money amount
  */
case class Money(var moneyAmount: Int) extends Resource {
  override def inc(level: Int): Unit = {
    moneyAmount += (level*INC_BY_10)
  }
  override def take(): Int = {
    val taken = moneyAmount
    moneyAmount = INIT_VALUE
    taken
  }
  override def dec(price: Int): Unit = moneyAmount -= price
  override def amount: Int = moneyAmount

}

/**
  * Implementation of resource food
  * @param foodAmount food amount
  */
case class Food(var foodAmount: Int) extends Resource {
  override def inc(level: Int): Unit ={
    foodAmount += (level*INC_BY_10)
  }
  override def take(): Int = {
    val taken = foodAmount
    foodAmount = INIT_VALUE
    taken
  }
  override def dec(price: Int): Unit = foodAmount -= price
  override def amount: Int = foodAmount
}




