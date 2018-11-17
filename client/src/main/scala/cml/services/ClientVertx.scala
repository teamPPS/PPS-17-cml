package cml.services

import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.ext.web.client.WebClient

/**
  * General web client fot utils micro-services request
  *
  * @author Chiara Volonnino
  */

trait ClientVertx extends ScalaVerticle {

  var client: WebClient = WebClient.create(vertx)

  // webClientOption per istanziare 2 opzioni - 1 per ogni servizio
}
