package cml.services.authentication

import cml.core.utils.JWTAuthentication
import cml.core.{RouterVerticle, RoutingOperation, TokenAuthentication}
import io.netty.handler.codec.http.HttpResponseStatus._
import io.vertx.core.Handler
import io.vertx.scala.ext.web.{Router, RoutingContext}

import scala.util.{Failure, Success}
import cml.services.authentication.utils.AuthenticationUrl._
import io.vertx.core.logging.{Logger, LoggerFactory}
import io.vertx.scala.ext.web.handler.BodyHandler

/**
  * This class implements AuthenticationVerticle
  *
  * @author Chiara Volonnino
  */

case class AuthenticationVerticle() extends RouterVerticle {

  private var authenticationService: AuthenticationService = _

  private val log: Logger = LoggerFactory.getLogger("Authentication Verticle")

  override def initializeRouter(router: Router): Unit = {
    router.route.handler(BodyHandler.create())
    router post RegisterApi handler register
    router put LoginApi handler login
    router delete  LogoutApi handler logout
    router get ValidationTokenApi handler validationToken
  }

  override def initializeService(): Unit = {
    authenticationService = AuthenticationService()
  }

  private def register: Handler[RoutingContext] = implicit routingContext => {
    log.info("Receive register request", None)
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
    log.info("Receive login request", None)
    (for (
      headerAuthorization <- getRequestAndHeader;
      (username, password) <- TokenAuthentication.checkBase64Authentication(headerAuthorization)
    ) yield {
      authenticationService.login(username, password).onComplete {
        case Success(x) =>
          if(x) JWTAuthentication.encodeUsernameToken(username).foreach(sendResponse(OK,_)) else {
            sendResponse(UNAUTHORIZED, UNAUTHORIZED.toString)
          }
        case Failure(_) => sendResponse(UNAUTHORIZED, UNAUTHORIZED.toString)
      }
    }).getOrElse(sendResponse(BAD_REQUEST, BAD_REQUEST.toString))
  }

  private def logout: Handler[RoutingContext] = implicit routingContext => {
    log.info("Receive logout request", None)
    (for (
      headerAuthorization <- getRequestAndHeader;
      token <- TokenAuthentication.checkAuthenticationToken(headerAuthorization);
      username <- JWTAuthentication.decodeUsernameToken(token)
    ) yield {
      sendResponse(OK, username)
    }).getOrElse(sendResponse(UNAUTHORIZED, UNAUTHORIZED.toString))
  }

  private def validationToken: Handler[RoutingContext] = implicit routingContext => {
    log.info("Receive validationToken request", None)
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

