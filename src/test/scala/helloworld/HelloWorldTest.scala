package helloworld

import org.scalatest.FunSuite

class HelloWorldTest extends FunSuite {

  test("testMain") {
    val left = 1
    val right = 1
    assert(left == right)
  }

}
