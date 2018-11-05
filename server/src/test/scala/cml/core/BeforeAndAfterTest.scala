package cml.core

import cml.services.authentication.AuthenticationVerticle
import cml.services.authentication.utils.AuthenticationUrl
import io.vertx.core.Vertx
import io.vertx.lang.scala.ScalaVerticle
import org.scalatest._

import scala.concurrent.Promise

class BeforeAndAfterTest extends VerticleTesting[AuthenticationVerticle] with BeforeAndAfter {

  val vertx = Vertx.vertx()

  before {
    println("service init")
    vertx.deployVerticle(ScalaVerticle.nameForVerticle[AuthenticationVerticle])
  }

  after{
    loginTest()
    validationTokenTest()
  }

  def loginTest(): Unit = {
    println("login test")
    val promiseValidation = Promise[String]
    vertx.createHttpClient()
      .getNow(8080, "127.0.0.1", AuthenticationUrl.VALIDATION_TOKEN_API,
        response => {
          response.exceptionHandler(promiseValidation.failure)
          response.bodyHandler(buffer => promiseValidation.success(buffer.toString))
          response.statusCode()
        })
    promiseValidation.future.map(res => {
      println("validation: " + res)
      assert(res equals HttpMessage.BAD_REQUEST)
    })
  }

  def validationTokenTest(): Unit = {
    val promiseLogin = Promise[String]
    vertx.createHttpClient()
      .getNow(8080, "127.0.0.1", AuthenticationUrl.LOGIN_API,
        response => {
          response.exceptionHandler(promiseLogin.failure)
          response.bodyHandler(buffer => promiseLogin.success(buffer.toString))
          response.statusCode()
        })
    promiseLogin.future.map(res => {
      println("login " + res)
      assert (res equals HttpMessage.BAD_REQUEST)
    })
  }

}
