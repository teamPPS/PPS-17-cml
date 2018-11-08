package cml.services.authentication

import cml.core.{JWTAuthentication, RouterVerticle, RoutingOperation, TokenAuthentication}
import io.netty.handler.codec.http.HttpResponseStatus._
import io.vertx.core.Handler
import io.vertx.scala.ext.web.{Router, RoutingContext}

import scala.concurrent.Future
import scala.util.{Failure, Success}
import cml.services.authentication.utils.AuthenticationConfig.AuthenticationUrl
import cml.core.HttpMessage

/**
  * This class implements AuthenticationVerticle
  *
  * @author Chiara Volonnino
  */

class AuthenticationVerticle extends RouterVerticle with RoutingOperation {

  private var authenticationService: Future[AuthenticationService] = _

  override def initializeRouter(router: Router): Unit = {
    router post AuthenticationUrl.REGISTER_API handler register
    router put AuthenticationUrl.LOGIN_API handler login
    router put AuthenticationUrl.LOGOUT_API handler logout
    router delete AuthenticationUrl.DELETE_API handler delete
    router get AuthenticationUrl.VALIDATION_TOKEN_API handler validationToken
  }

  private def register: Handler[RoutingContext] = implicit routingContext => {
    println("Receive register request")
    (for(
      request <- getRequestAndHeader;
      (username, password) <- TokenAuthentication.checkBase64Authentication(request)
    ) yield {
      authenticationService flatMap(_.register(username, password)) onComplete {
        case Success(_) =>
          JWTAuthentication.encodeUsernameToken(username).foreach(sendResponse(CREATED,_))
        case Failure(_) => sendResponse(BAD_REQUEST, "")
      }
    }).getOrElse(sendResponse(BAD_REQUEST, HttpMessage.BAD_REQUEST))
  }

  private def login: Handler[RoutingContext] = implicit routingContext => {
    println("Receive login request")
    (for (
      headerAuthorization <- getRequestAndHeader;
      (username, password) <- TokenAuthentication.checkBase64Authentication(headerAuthorization)
    ) yield {
      authenticationService flatMap(_.login(username, password)) onComplete {
        case Success(_) =>
          JWTAuthentication.encodeUsernameToken(username).foreach(sendResponse(OK,_))
        case Failure(_) => sendResponse(UNAUTHORIZED, HttpMessage.UNAUTHORIZED)
      }
    }).getOrElse(sendResponse(BAD_REQUEST, HttpMessage.BAD_REQUEST))
  }

  private def logout: Handler[RoutingContext] = implicit routingContext => {
    println("Receive logout request")

  }

  private def delete: Handler[RoutingContext] = implicit routingContext => {
    println("Receive delete request")
    (for (
      headerAuthorization <- getRequestAndHeader;
      token <- TokenAuthentication.checkAuthenticationToken(headerAuthorization);
      username <- JWTAuthentication.decodeUsernameToken(token)
    ) yield {
      authenticationService map(_.delete(username)) onComplete {
        case Success(_) => sendResponse(OK, username)
        case Failure(_) => sendResponse(UNAUTHORIZED, HttpMessage.UNAUTHORIZED)
      }
    }).getOrElse(sendResponse(BAD_REQUEST, HttpMessage.BAD_REQUEST))
  }

  private def validationToken: Handler[RoutingContext] = implicit routingContext => {
    println("Receive validationToken request")
    (for (
      headerAuthorization <- getRequestAndHeader;
      token <- TokenAuthentication.checkAuthenticationToken(headerAuthorization);
      username <- JWTAuthentication.decodeUsernameToken(token)
    ) yield {
      authenticationService map(_.validationToken(username)) onComplete {
        case Success(_) => sendResponse(OK, username)
        case Failure(_) => sendResponse(UNAUTHORIZED, HttpMessage.UNAUTHORIZED)
      }
    }).getOrElse(sendResponse(BAD_REQUEST, HttpMessage.BAD_REQUEST))
  }
}
