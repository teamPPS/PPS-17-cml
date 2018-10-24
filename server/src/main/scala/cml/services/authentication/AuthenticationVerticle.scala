package cml.services.authentication

import cml.core.RouterVerticle
import io.vertx.core.Handler
import io.vertx.scala.ext.web.{Router, RoutingContext}

/**
  * This class implements AuthenticationVerticle
  *
  * @author Chiara Volonnino
  */

class AuthenticationVerticle extends RouterVerticle {

  private val GENERAL_PATH = "/api/authentication"
  private val REGISTER_API = "/register"
  private val LOGIN_API = "/login"
  private val LOGOUT_API = "/logout"

  override def initializeRouter(router: Router): Unit = {
    router post GENERAL_PATH + REGISTER_API handler register
    router get GENERAL_PATH + LOGIN_API handler login
    router put GENERAL_PATH + LOGOUT_API handler logout
  }

  // Bisogna gestire tutte le richieste
  private def register: Handler[RoutingContext] = implicit routingContext => {
    println("Receive register request")

  }
  private def login: Handler[RoutingContext] = implicit routingContext => {
    println("Receive register login")

  }
  private def logout: Handler[RoutingContext] = implicit routingContext => {
    println("Receive register logout")

  }
}
