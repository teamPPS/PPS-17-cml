package cml.services

import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.ext.web.client.{WebClient, WebClientOptions}

/**
  * General web client fot utils micro-services request
  *
  * @author Chiara Volonnino
  */

trait ClientVertx extends ScalaVerticle {

  var Client: WebClient = _

  var optionClient: WebClientOptions // uno per ogni servizio? optionClient (AuthenticationPort) && optionClient(ServerPort)




}
