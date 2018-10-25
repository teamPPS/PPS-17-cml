package cml.core

import io.netty.handler.codec.http.{HttpHeaderNames, HttpResponseStatus}
import io.vertx.scala.core.http.HttpServerResponse
import io.vertx.scala.ext.web.RoutingContext

/**
  * This is useful to implement AuthenticationVerticle
  *
  * @author Chiara Volonnino
  */
trait CheckOperation {

  /**
    *
    * @param routingContext
    * @return
    */
  def gerHeaderRequest(implicit routingContext: RoutingContext): Option[String] = {
    routingContext.request().getHeader(HttpHeaderNames.AUTHORIZATION.toString())
  }

  /**
    * Get a response
    *
    * @param routingContext routing context
    * @return the response
    */
  def getResponse(implicit routingContext: RoutingContext): HttpServerResponse = routingContext.response

  /**
    * Send a response to client
    *
    * @param httpCode http code response
    * @param message
    */
  def sendResponse(httpCode: HttpResponseStatus, message: String): Unit = ???
}

