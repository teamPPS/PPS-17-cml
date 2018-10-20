package helloworld

import org.scalatest.FunSuite

class AwesomenessTest extends FunSuite {

  test("testMultiple") {
    val a = new Awesomeness
    assert((a multiple(1,2)) == 2)
  }

  test("testSquare") {
    val a = new Awesomeness
    assert((a square 1) == 1)
  }

}
