package cml.services.village

import cml.core.RouterVerticle
import io.vertx.core.Handler
import io.vertx.scala.ext.web.{Router, RoutingContext}

/**
  * This class implement VillagesVerticle
  *
  * @author ecavina
  */
class VillageVerticle extends RouterVerticle {

  private val GENERAL_PATH = "/api/villages"
  private val CREATE_VILLAGE = "/villages"
  private val ENTER_VILLAGE = "/village/" //id?
  private val EXIT_VILLAGE = "/village/" //id
  //TODO village interactions
  //TODO check token before interactions

  private val villageService = VillageService()

  /**
    * Initialize router
    *
    * @param router is router to initialize
    */
  override def initializeRouter(router: Router): Unit = {
    router post GENERAL_PATH + CREATE_VILLAGE  handler create
    router put ENTER_VILLAGE handler enter
    router put EXIT_VILLAGE handler exit
  }

  //qui ci vanno gli handler con routing context e richiamano i metodi in villageservice

  private def create: Handler[RoutingContext] = implicit routingContext => {
    println("Request to create village ", routingContext request())
    routingContext response() end "creazione completata"
  }

  private def enter: Handler[RoutingContext] = implicit routingContext => {
    println("Request to enter village ", routingContext request())
    routingContext response() end "enter village completata"
  }

  private def exit: Handler[RoutingContext] = implicit routingContext => {
    println("Request to exit village ", routingContext request())
    routingContext response() end "exit completata"
  }

  def checkUserAuthorizationToken(): Boolean = true // dummy
}
