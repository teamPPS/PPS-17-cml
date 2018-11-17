package cml.model

import java.util.{Timer, TimerTask}

import cml.model.base.{Food, Money}
import org.scalatest.FunSuite

/**
  * @author Monica Gondolini
  */
class ResourceTest extends FunSuite{

  test("Resource production test") {
    val food = Food(0)
    val money = Money(0)

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

    val foodTaken = food.take()
    println("food taken "+foodTaken+ " food now: " +food.value)

    val moneyTaken = money.take()
    println("money taken "+moneyTaken+ " money now: " +money.value)
  }

}
