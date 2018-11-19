package cml.model

import java.util.{Timer, TimerTask}

import cml.model.base.{Food, Money}
import cml.utils.ModelConfig.Resource.INIT_VALUE
import org.scalatest.FunSuite

/**
  * @author Monica Gondolini
  */
class ResourceTest extends FunSuite{
/*

  test("Resource production during time test") {
    val food = Food(INIT_VALUE)
    val money = Money(INIT_VALUE)

    val timer = new Timer()
    val task = new TimerTask {
      def run(): Unit = {
        food.inc()
        money.inc()
        println("food " + food.amount)
        println("money " + money.amount)
      }
    }
    timer.schedule(task, 0, 1000L)


    val timer1 = new Timer()
    val task1 = new TimerTask {
      def run(): Unit = {
        food.take()
        money.take()
        println("food " + food.amount)
        println("money " + money.amount)
      }
    }
    timer1.schedule(task1, 0, 1000L )

    Thread.sleep(100000)

    assert(food.amount > INIT_VALUE && money.amount > INIT_VALUE)
  }

  test("Resource retrieve test"){
    val food = Food(INIT_VALUE)
    val money = Money(INIT_VALUE)

    for(i <- 1 to 10 ){
      food.inc()
      money.inc()
    }

    val foodTaken = food.take()
    println("food taken: "+foodTaken+ " food amount now: " +food.amount)

    val moneyTaken = money.take()
    println("money taken: "+moneyTaken+ " money amount now: " +money.amount)

    assert(food.amount.equals(INIT_VALUE) && money.amount.equals(INIT_VALUE))
  }

*/


}
