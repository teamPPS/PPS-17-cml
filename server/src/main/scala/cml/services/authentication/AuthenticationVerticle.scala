package cml.services.authentication

import cml.core.utils.JWTAuthentication
import cml.core.{RouterVerticle, RoutingOperation, TokenAuthentication}
import io.netty.handler.codec.http.HttpResponseStatus._
import io.vertx.core.Handler
import io.vertx.scala.ext.web.{Router, RoutingContext}

import scala.util.{Failure, Success}
import cml.services.authentication.utils.AuthenticationUrl._

/**
  * This class implements AuthenticationVerticle
  *
  * @author Chiara Volonnino
  */

case class AuthenticationVerticle() extends RouterVerticle with RoutingOperation {

  private var authenticationService: AuthenticationService = _

  override def initializeRouter(router: Router): Unit = {
    router post RegisterApi handler register
    router put LoginApi handler login
    router delete  LogoutApi handler logout
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
        case Failure(_) => sendResponse(BAD_REQUEST, BAD_REQUEST.toString)
      }
    }).getOrElse(sendResponse(BAD_REQUEST, BAD_REQUEST.toString))
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
        case Failure(_) => sendResponse(UNAUTHORIZED, UNAUTHORIZED.toString)
      }
    }).getOrElse(sendResponse(BAD_REQUEST, BAD_REQUEST.toString))
  }

  private def logout: Handler[RoutingContext] = implicit routingContext => {
    println("Receive logout request") // delete only token da valutare, così non va bene! o salviamo il token nel db o non soS
    (for (
      headerAuthorization <- getRequestAndHeader;
      token <- TokenAuthentication.checkAuthenticationToken(headerAuthorization);
      username <- JWTAuthentication.decodeUsernameToken(token)
    ) yield {
      authenticationService.logout(username).onComplete {
        case Success(_) => sendResponse(OK, username)
        case Failure(_) => sendResponse(UNAUTHORIZED, UNAUTHORIZED.toString)
      }
    }).getOrElse(sendResponse(BAD_REQUEST, BAD_REQUEST.toString))
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
        case Failure(_) => sendResponse(UNAUTHORIZED, UNAUTHORIZED.toString)
      }
    }).getOrElse(sendResponse(BAD_REQUEST, BAD_REQUEST.toString))
  }
}
