package cml.services.authentication

/**
  * This test class mach AuthenticationVerticle class is correct
  *
  * @author Chiara Volonnino
  */
import cml.core.{BeforeAndAfterTest, HttpMessage, VerticleTesting}
import cml.services.authentication.utils.AuthenticationConfig.AuthenticationUrl
import io.vertx.core.{Vertx, http}
import io.vertx.scala.core.http._

import scala.concurrent.Promise

class AuthenticationVerticleTest extends BeforeAndAfterTest {

  test("Validation token test") {
    println("Validation Token test")
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
  }

  test("Registration test"){
    println("Registration test")
    val vertx: Vertx = Vertx.vertx()
    val promiseRegister = Promise[String]

    val body: String = "{'username':'pps', 'password':'cml'}"

    vertx.createHttpClient().post(8080, "127.0.0.1", AuthenticationUrl.REGISTER_API)
      .putHeader("content-length", "1000")
      .handler(response =>{
          response.exceptionHandler(promiseRegister.failure)
          response.bodyHandler(buffer => promiseRegister.success(buffer.toString))
          response.statusCode()
      }).write(body).end()

    promiseRegister.future.map(res=>{
      println("registration: " + res)
      assert(res equals HttpMessage.BAD_REQUEST)
    })
  }

  test("Login test"){
    println("Login test")
    val vertx: Vertx = Vertx.vertx()
    val promiseLogin = Promise[String]
    val body: String = "{'username':'pps', 'password':'cml'}"

    vertx.createHttpClient().put(8080, "127.0.0.1", AuthenticationUrl.LOGIN_API)
      .putHeader("content-length", "1000")
      .handler(response =>{
        response.exceptionHandler(promiseLogin.failure)
        response.bodyHandler(buffer => promiseLogin.success(buffer.toString))
        response.statusCode()
      }).write(body).end()

    promiseLogin.future.map(res=>{
      println("login: " + res)
      assert(res equals HttpMessage.BAD_REQUEST)
    })
  }

//  test("Delete test"){
//    println("Delete test")
//    val vertx: Vertx = Vertx.vertx()
//    val promiseDelete = Promise[String]
//    val body: String = "{'username':'pps', 'password':'cml'}"
//
//    vertx.createHttpClient().delete(8080, "127.0.0.1", AuthenticationUrl.LOGIN_API)
//      .putHeader("content-length", "1000")
//      .handler(response =>{
//        response.exceptionHandler(promiseDelete.failure)
//        response.bodyHandler(buffer => promiseDelete.success(buffer.toString))
//        response.statusCode()
//      }).write(body).end()
//
//    promiseDelete.future.map(res=>{
//      println("delete: " + res)
//      assert(res equals HttpMessage.BAD_REQUEST)
//    })
//  }
}
