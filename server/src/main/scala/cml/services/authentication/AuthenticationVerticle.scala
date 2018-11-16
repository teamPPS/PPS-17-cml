package cml.services.authentication

import cml.core.{JWTAuthentication, RouterVerticle, RoutingOperation, TokenAuthentication}
import io.netty.handler.codec.http.HttpResponseStatus._
import io.vertx.core.Handler
import io.vertx.scala.ext.web.{Router, RoutingContext}

import scala.util.{Failure, Success}
import cml.services.authentication.utils.AuthenticationUrl.AuthenticationUrl._
import cml.core.utils.HttpMessage._
import cml.services.authentication.AuthenticationService.AuthenticationServiceImpl

import scala.concurrent.Future

/**
  * This class implements AuthenticationVerticle
  *
  * @author Chiara Volonnino
  */

class AuthenticationVerticle extends RouterVerticle with RoutingOperation {

  private var authenticationService: AuthenticationService = _

  override def initializeRouter(router: Router): Unit = {
    router post RegisterApi handler register
    router put LoginApi handler login
    router delete LogoutApi handler logout
    router delete DeleteApi handler delete
    router get ValidationTokenApi handler validationToken
  }

  override def initializeService: Unit = {
    authenticationService = AuthenticationService()
  }

  private def register: Handler[RoutingContext] = implicit routingContext => {
    println("Receive register request")
    (for(
      request <- getRequestAndHeader;
      (username, password) <- TokenAuthentication.checkBase64Authentication(request)
    ) yield {
      authenticationService.register(username,password).onComplete {
        case Success(_) =>
          JWTAuthentication.encodeUsernameToken(username).foreach(sendResponse(CREATED,_))
        case Failure(_) => sendResponse(BAD_REQUEST, BadRequest)
      }
    }).getOrElse(sendResponse(BAD_REQUEST, BadRequest))
  }

  private def login: Handler[RoutingContext] = implicit routingContext => {
    println("Receive login request")
    (for (
      headerAuthorization <- getRequestAndHeader;
      (username, password) <- TokenAuthentication.checkBase64Authentication(headerAuthorization)
    ) yield {
      authenticationService.login(username, password).onComplete {
        case Success(_) =>
          JWTAuthentication.encodeUsernameToken(username).foreach(sendResponse(OK,_))
        case Failure(_) => sendResponse(UNAUTHORIZED, Unauthorized)
      }
    }).getOrElse(sendResponse(BAD_REQUEST, BadRequest))
  }

  private def logout: Handler[RoutingContext] = implicit routingContext => {
    println("Receive logout request") // delete only token
    (for (
      headerAuthorization <- getRequestAndHeader;
      token <- TokenAuthentication.checkAuthenticationToken(headerAuthorization);
      username <- JWTAuthentication.decodeUsernameToken(token)
    ) yield {
      authenticationService.delete(username).onComplete {
        case Success(_) => sendResponse(OK, username)
        case Failure(_) => sendResponse(UNAUTHORIZED, Unauthorized)
      }
    }).getOrElse(sendResponse(BAD_REQUEST, BadRequest))
  }

  private def delete: Handler[RoutingContext] = implicit routingContext => {
    println("Receive delete request") // delete all
    (for (
      headerAuthorization <- getRequestAndHeader;
      token <- TokenAuthentication.checkAuthenticationToken(headerAuthorization);
      username <- JWTAuthentication.decodeUsernameToken(token)
    ) yield {
      authenticationService.delete(username).onComplete {
        case Success(_) => sendResponse(OK, username)
        case Failure(_) => sendResponse(UNAUTHORIZED, Unauthorized)
      }
    }).getOrElse(sendResponse(BAD_REQUEST, BadRequest))
  }

  private def validationToken: Handler[RoutingContext] = implicit routingContext => {
    println("Receive validationToken request")
    (for (
      headerAuthorization <- getRequestAndHeader;
      token <- TokenAuthentication.checkAuthenticationToken(headerAuthorization);
      username <- JWTAuthentication.decodeUsernameToken(token)
    ) yield {
      authenticationService.validationToken(username).onComplete {
        case Success(_) => sendResponse(OK, username)
        case Failure(_) => sendResponse(UNAUTHORIZED, Unauthorized)
      }
    }).getOrElse(sendResponse(BAD_REQUEST, BadRequest))
  }
}
