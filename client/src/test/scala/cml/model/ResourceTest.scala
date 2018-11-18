package cml.model

import java.util.{Timer, TimerTask}

import cml.model.base.{Food, Money}
import cml.utils.ModelConfig.Resource.INIT_VALUE
import org.scalatest.FunSuite

/**
  * @author Monica Gondolini
  */
class ResourceTest extends FunSuite{

  test("Resource production during time test") {
    val food = Food(INIT_VALUE)
    val money = Money(INIT_VALUE)

    val timer = new Timer()
    val task = new TimerTask {
      def run(): Unit = {
        food.inc()
        money.inc()
        println("food " + food.value)
        println("money " + money.value)
      }
    }
    timer.schedule(task, 0, 1000L)
    Thread.sleep(10000)

    assert(food.value > INIT_VALUE && money.value > INIT_VALUE)
  }

  test("Resource retrieve test"){
    val food = Food(INIT_VALUE)
    val money = Money(INIT_VALUE)

    for(i <- 1 to 10 ){
      food.inc()
      money.inc()
    }

    val foodTaken = food.take()
    println("food taken: "+foodTaken+ " food amount now: " +food.value)

    val moneyTaken = money.take()
    println("money taken: "+moneyTaken+ " money amount now: " +money.value)

    assert(food.value.equals(INIT_VALUE) && money.value.equals(INIT_VALUE))
  }



}
