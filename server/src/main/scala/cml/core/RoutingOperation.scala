package cml.core

import io.netty.handler.codec.http.{HttpHeaderNames, HttpResponseStatus}
import io.vertx.scala.core.http.HttpServerResponse
import io.vertx.scala.ext.web.RoutingContext
import scala.concurrent.Future

/**
  * This trait is useful to implement AuthenticationVerticle
  *
  * @author Chiara Volonnino
  */
trait RoutingOperation {

  /**
    * Get a header request
    *
    * @param routingContext is implicit routing context
    * @return A Optional with the header or None
    */
  def getRequestAndHeader(implicit routingContext: RoutingContext): Option[String] = {
    routingContext.request().getHeader(HttpHeaderNames.AUTHORIZATION.toString())
  }


  /**
    * Get a response
    *
    * @param routingContext routing context
    * @return the response
    */
  def getResponse(implicit routingContext: RoutingContext): HttpServerResponse = {
    routingContext.response
  }

  /**
    * Send a response to client
    *
    * @param httpCode http code response
    * @param message to send to the client
    */
  def sendResponse(httpCode: HttpResponseStatus, message: String)(implicit routingContext: RoutingContext): Unit = {
    val code = httpCode.code()
    getResponse.setStatusCode(code).end(message)
  }


  //abstract def validate(input: String): Future[String]  ---> da spostare in altra classe

  // da implementare in VillageVerticle per controllo user - sicuro da modificare
  def checkAuthenticationHandler(implicit routingContext: RoutingContext): Future[String] =
    getRequestAndHeader match {
      case Some(authenticationHeader) => Future.successful(authenticationHeader)
      case None => Future.failed(new Exception)
    }
}

