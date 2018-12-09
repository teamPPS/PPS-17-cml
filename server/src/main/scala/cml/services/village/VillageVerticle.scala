package cml.services.village

import cml.core.utils.JWTAuthentication
import cml.core.{RouterVerticle, RoutingOperation, TokenAuthentication}
import io.vertx.core.Handler
import io.vertx.scala.ext.web.{Router, RoutingContext}
import cml.services.village.utils.VillageUrl._
import cml.services.authentication.utils.AuthenticationUrl._
import io.netty.handler.codec.http.HttpResponseStatus
import io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST
import io.vertx.scala.ext.web.handler.BodyHandler
import play.api.libs.json.Json

import scala.util.{Failure, Success}

/**
  * This class implement VillagesVerticle
  *
  * @author ecavina
  */
case class VillageVerticle() extends RouterVerticle with RoutingOperation {

  private var villageService: VillageService = _

  /**
    * Initialize router
    *
    * @param router is router to initialize
    */

  override def initializeRouter(router: Router): Unit = {
    router.route.handler(BodyHandler.create())
    router get VillagesAPI handler enter
    router post VillagesAPI handler create
    router put VillagesAPI handler update
    router put SetUpdateAPI handler setUpdate
    router delete VillagesAPI handler delete
    router put LogoutApi handler exit
  }

  override def initializeService: Unit = {
    villageService =  VillageService()
  }

  private def create: Handler[RoutingContext] = implicit routingContext => {
    println("Request to create village")
    (for(
      headerAuthorization <- getRequestAndHeader;
      token <- TokenAuthentication.checkAuthenticationToken(headerAuthorization);
      username <- JWTAuthentication.decodeUsernameToken(token)
    ) yield {
        villageService.createVillage(username).onComplete {
          case Success(document) =>
            getResponse.putHeader("content-type", "application/json; charset=utf-8")
              .setStatusCode(HttpResponseStatus.CREATED.code())
              .end(document)
          case Failure(_) => sendResponse(HttpResponseStatus.INTERNAL_SERVER_ERROR, "Can't create a new village")
        }
    }).getOrElse(sendResponse(BAD_REQUEST, BAD_REQUEST.toString))
}

  private def enter: Handler[RoutingContext] = implicit routingContext => {
    println("Request to enter village")
    (for(
      headerAuthorization <- getRequestAndHeader;
      token <- TokenAuthentication.checkAuthenticationToken(headerAuthorization);
      username <- JWTAuthentication.decodeUsernameToken(token)
    ) yield {
      villageService.enterVillage(username).onComplete {
        case Success(document) =>
          println(Json.parse(document).toString())
          getResponse.putHeader("content-type", "application/json; charset=utf-8")
            .setStatusCode(HttpResponseStatus.OK.code())
            .end(Json.parse(document).toString())
        case Failure(_) => sendResponse(HttpResponseStatus.INTERNAL_SERVER_ERROR, "Server Error")
      }
    }).getOrElse(sendResponse(BAD_REQUEST, BAD_REQUEST.toString))
  }

  private def update: Handler[RoutingContext] = implicit routingContext => {
    println("Request to update village")
    (for(
      headerAuthorization <- getRequestAndHeader;
      body <- getRequestAndBody;
      if !body.isEmpty;
      token <- TokenAuthentication.checkAuthenticationToken(headerAuthorization);
      username <- JWTAuthentication.decodeUsernameToken(token)
    ) yield {
      villageService.updateVillage(username, body).onComplete {
        case Success(value) => if (value) {
          sendResponse(HttpResponseStatus.OK, "Update done")
        } else {
          sendResponse(BAD_REQUEST, "Error while update")
        }
        case Failure(_) => {
          sendResponse(HttpResponseStatus.INTERNAL_SERVER_ERROR, "Server Error")
        }
      }
    }).getOrElse(sendResponse(BAD_REQUEST, BAD_REQUEST.toString))
  }

  private def setUpdate: Handler[RoutingContext] = implicit routingContext => {
    println("Request to set update village")
    (for(
      headerAuthorization <- getRequestAndHeader;
      body <- getRequestAndBody;
      if !body.isEmpty;
      token <- TokenAuthentication.checkAuthenticationToken(headerAuthorization);
      username <- JWTAuthentication.decodeUsernameToken(token)
    ) yield {
      villageService.setUpdateVillage(username, body).onComplete {
        case Success(value) => if (value) {
          sendResponse(HttpResponseStatus.OK, "Update set done")
        } else {
          sendResponse(BAD_REQUEST, "Error while update set")
        }
        case Failure(_) => {
          sendResponse(HttpResponseStatus.INTERNAL_SERVER_ERROR, "Server Error")
        }
      }
    }).getOrElse(sendResponse(BAD_REQUEST, BAD_REQUEST.toString))
  }

  private def delete: Handler[RoutingContext] = implicit routingContext => {
    println("Request to enter village")
    (for(
      headerAuthorization <- getRequestAndHeader;
      token <- TokenAuthentication.checkAuthenticationToken(headerAuthorization);
      username <- JWTAuthentication.decodeUsernameToken(token)
    ) yield {
      villageService.deleteVillageAndUser(username).onComplete {
        case Success(value) => if (value) {
          sendResponse(HttpResponseStatus.ACCEPTED, "Deleted")
        } else {
          sendResponse(BAD_REQUEST, "Error while deleting")
        }
        case Failure(_) => sendResponse(HttpResponseStatus.INTERNAL_SERVER_ERROR, "Server Error")
      }
    }).getOrElse(sendResponse(BAD_REQUEST, BAD_REQUEST.toString))
  }

  private def exit: Handler[RoutingContext] = implicit routingContext => {
    println("Request to exit village ", routingContext request())
  }

}
