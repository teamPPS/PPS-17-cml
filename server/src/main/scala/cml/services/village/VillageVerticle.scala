package cml.services.village

import cml.core.utils.JWTAuthentication
import cml.core.{RouterVerticle, RoutingOperation, TokenAuthentication}
import io.vertx.core.Handler
import io.vertx.scala.ext.web.{Router, RoutingContext}
import cml.services.village.utils.VillageUrl._
import cml.services.authentication.utils.AuthenticationUrl._

/**
  * This class implement VillagesVerticle
  *
  * @author ecavina
  */
case class VillageVerticle() extends RouterVerticle with RoutingOperation {

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
    villageService =  _
  }

  private def create: Handler[RoutingContext] = implicit routingContext => {
    println("Request to create village ", routingContext request())
    for(
      headerAuthorization <- getRequestAndHeader;
      token <- TokenAuthentication.checkAuthenticationToken(headerAuthorization);
      username <- JWTAuthentication.decodeUsernameToken(token)
    ) yield username
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

}
