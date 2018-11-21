package cml.services

import cml.services.authentication.utils.AuthenticationUrl
import cml.core.utils.NetworkConfiguration._
import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.ext.web.client.{WebClient, WebClientOptions}

/**
  * General web client fot utils micro-services request
  *
  * @author Chiara Volonnino
  */

abstract class ClientVertx extends ScalaVerticle {

  var authenticationClient: WebClient = WebClient.create(vertx, authenticationOpt)

  var authenticationOpt = WebClientOptions()
  authenticationOpt.setDefaultPort(AuthenticationServicePort).setDefaultHost(ServiceHostForRequest)

  var villageOpt = WebClientOptions()
  villageOpt.setDefaultPort(8000).setDefaultHost("127.0.0.1")
  // webClientOption per istanziare 2 opzioni - 1 per ogni servizio
}
