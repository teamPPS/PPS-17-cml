package cml.services.village

import cml.core.RouterVerticle
import io.vertx.core.Handler
import io.vertx.scala.ext.web.{Router, RoutingContext}
import cml.services.village.utils.VillageUrl._
import cml.services.authentication.utils.AuthenticationUrl._

/**
  * This class implement VillagesVerticle
  *
  * @author ecavina
  */
class VillageVerticle extends RouterVerticle {

  //TODO village interactions
  //TODO check token before interactions

  private var villageService: VillageService = _

  /**
    * Initialize router
    *
    * @param router is router to initialize
    */

  override def initializeRouter(router: Router): Unit = {
    router get VillagesAPI handler enter
    router post VillagesAPI handler create
    router put VillagesAPI handler update
    router delete VillagesAPI handler delete
    router put LogoutApi handler exit
  }

  override def initializeService: Unit = {

  }

  //qui ci vanno gli handler con routing context e richiamano i metodi in villageservice

  //usare le routing operation di chiara qui
  private def create: Handler[RoutingContext] = implicit routingContext => {
    println("Request to create village ", routingContext request())
  }

  private def enter: Handler[RoutingContext] = implicit routingContext => {
    println("Request to enter village ", routingContext request())
  }

  private def update: Handler[RoutingContext] = implicit routingContext => {
    println("Request to enter village ", routingContext request())
  }

  private def delete: Handler[RoutingContext] = implicit routingContext => {
    println("Request to enter village ", routingContext request())
  }

  private def exit: Handler[RoutingContext] = implicit routingContext => {
    println("Request to exit village ", routingContext request())
  }

  def checkUserAuthorizationToken(): Boolean = true // dummy
  /**
    * Initializes the services
    *
    * @return
    */
}
