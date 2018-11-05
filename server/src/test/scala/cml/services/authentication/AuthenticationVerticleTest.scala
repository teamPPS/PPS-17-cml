package cml.services.authentication

/**
  * This test class mach AuthenticationVerticle class is correct
  *
  * @author Chiara Volonnino
  */
import cml.core.{BeforeAndAfterTest, HttpMessage, VerticleTesting}
import cml.services.authentication.utils.AuthenticationUrl
import io.vertx.core.Vertx

import scala.concurrent.Promise

class AuthenticationVerticleTest extends BeforeAndAfterTest {

/*  test("Login test") {
    val vertx: Vertx = Vertx.vertx()
    val promise = Promise[String]
    vertx.createHttpClient()
      .getNow(8080, "127.0.0.1", AuthenticationUrl.LOGIN_API,
        response => {
          response.exceptionHandler(promise.failure)
          response.bodyHandler(buffer => promise.success(buffer.toString))
        })
    promise.future.map(res => {
      println("login " + res)
      assert (res equals HttpMessage.BAD_REQUEST)
    })
  }*/

  test("Validation token test") {
    val vertx: Vertx = Vertx.vertx()
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
