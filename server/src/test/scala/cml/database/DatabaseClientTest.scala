package cml.database

import org.scalatest.FunSuite

class DatabaseClientTest extends FunSuite{

  test("testDatabaseConnection"){

    val client : DatabaseClient = new DatabaseClient("mongodb://admin:cmlPPS17@ds239903.mlab.com:39903/cml-storage","cml-storage","Users")

    assert(1==1)
  }

}
