package cml.core

import io.netty.handler.codec.http.{HttpHeaderNames, HttpResponseStatus}
import io.vertx.scala.core.http.HttpServerResponse
import io.vertx.scala.ext.web.RoutingContext
import scala.concurrent.Future

/**
  * This trait is useful to implement services
  *
  * @author Chiara Volonnino
  */
trait RoutingOperation {

  /**
    * Get the header of the request
    * @param routingContext implicit routing context
    * @return A Optional with the header or None
    */
  def getRequestAndHeader(implicit routingContext: RoutingContext): Option[String] = {
    routingContext.request().getHeader(HttpHeaderNames.AUTHORIZATION.toString())
  }

  /**
    * Get the body of the request as a String
    * @param routingContext implicit routing context
    * @return A Option with the body as String or None
    */
  def getRequestAndBody(implicit routingContext: RoutingContext): Option[String] = {
    routingContext.getBodyAsString()
  }

  /**
    * Get a response
    * @param routingContext implicit routing context
    * @return the server response
    */
  def getResponse(implicit routingContext: RoutingContext): HttpServerResponse = {
    routingContext.response
  }

  /**
    * Send a response to client
    * @param httpCode http code response
    * @param message to send to the client
    * @param routingContext implicit routing context
    */
  def sendResponse(httpCode: HttpResponseStatus, message: String)(implicit routingContext: RoutingContext): Unit = {
    val code = httpCode.code()
    getResponse.setStatusCode(code).end(message)
  }

  /**
    * Check if the handler is correct
    * @param routingContext implicit routing context
    * @return handler
    */
  def checkAuthenticationHandler(implicit routingContext: RoutingContext): Future[String] =
    getRequestAndHeader match {
      case Some(authenticationHeader) => Future.successful(authenticationHeader)
      case None => Future.failed(new Exception)
    }
}

