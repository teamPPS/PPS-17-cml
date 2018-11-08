package cml.services.authentication

/**
  * This test class mach AuthenticationVerticle class is correct
  *
  * @author Chiara Volonnino
  */
import cml.core.{BeforeAndAfterTest, HttpMessage, VerticleTesting}
import cml.services.authentication.utils.AuthenticationConfig.AuthenticationUrl
import io.vertx.core.Vertx

import scala.concurrent.Promise

class AuthenticationVerticleTest extends BeforeAndAfterTest {

  test("Validation token test") {
    val vertx: Vertx = Vertx.vertx()
    val promiseValidation = Promise[String]
    vertx.createHttpClient()
      .getNow(8080, "127.0.0.1", AuthenticationUrl.VALIDATION_TOKEN_API, //REGISTER_API per test registrazione
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
}
