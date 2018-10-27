package cml.services.authentication

import cml.core.{RoutingOperation, JWTAuthentication, RouterVerticle, TokenAuthentication}
import io.netty.handler.codec.http.HttpResponseStatus._
import io.vertx.core.Handler
import io.vertx.scala.ext.web.{Router, RoutingContext}
import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
  * This class implements AuthenticationVerticle
  *
  * @author Chiara Volonnino
  */

class AuthenticationVerticle extends RouterVerticle with RoutingOperation {

  private val GENERAL_PATH = "/api/authentication"
  private val REGISTER_API = "/register"
  private val LOGIN_API = "/login"
  private val LOGOUT_API = "/logout"
  private val DELETE_API = "/delete"
  private val VALIDATION_TOKEN_API = "/validationToken"

  private var authenticationService: Future[AuthenticationService] = _

  override def initializeRouter(router: Router): Unit = {
    router post GENERAL_PATH + REGISTER_API handler register
    router get GENERAL_PATH + LOGIN_API handler login
    router put GENERAL_PATH + LOGOUT_API handler logout
    router delete GENERAL_PATH + DELETE_API handler delete
    router get GENERAL_PATH + VALIDATION_TOKEN_API handler validationToken
  }

  private def register: Handler[RoutingContext] = implicit routingContext => {
    println("Receive register request")
    for(
      request <- getRequestAndHeader;
      (username, password) <- TokenAuthentication.checkBase64Authentication(request)
    ) yield {
      authenticationService.flatMap(_.register(username, password)).onComplete {
        case Success(_) =>
          JWTAuthentication.encodeUsernameToken(username).foreach(sendResponse(CREATED,_))
        case Failure(_) => sendResponse(BAD_REQUEST, "")
      }
    }
  }

  private def login: Handler[RoutingContext] = implicit routingContext => {
    println("Receive login request")
    for (
      headerAuthorization <- getRequestAndHeader;
      (username, password) <- TokenAuthentication.checkBase64Authentication(headerAuthorization)
    ) yield {
      authenticationService.flatMap(_.login(username, password)).onComplete {
        case Success(_) =>
          JWTAuthentication.encodeUsernameToken(username).foreach(sendResponse(OK,_))
        case Failure(_) => sendResponse(UNAUTHORIZED, "")
      }
    }
  }

  private def logout: Handler[RoutingContext] = implicit routingContext => {
    println("Receive logout request")

  }

  private def delete: Handler[RoutingContext] = implicit routingContext => {
    println("Receive delete request")
    for (
      headerAuthorization <- getRequestAndHeader;
      token <- TokenAuthentication.checkAuthenticationToken(headerAuthorization);
      username <- JWTAuthentication.decodeUsernameToken(token)
    ) yield {
      authenticationService.map(_.delete(username)).onComplete {
        case Success(_) => sendResponse(OK, username)
        case Failure(_) => sendResponse(UNAUTHORIZED, "")
      }
    }
  }

  private def validationToken: Handler[RoutingContext] = implicit routingContext => {
    println("Receive validationToken request")
    for (
      headerAuthorization <- getRequestAndHeader;
      token <- TokenAuthentication.checkAuthenticationToken(headerAuthorization);
      username <- JWTAuthentication.decodeUsernameToken(token)
    ) yield {
      authenticationService.map(_.validationToken(username)).onComplete {
        case Success(_) => sendResponse(OK, username)
        case Failure(_) => sendResponse(UNAUTHORIZED, "")
      }
    }

  }
}
