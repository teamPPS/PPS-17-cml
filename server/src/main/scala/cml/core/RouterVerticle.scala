package cml.core

import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.core.http.HttpServer
import io.vertx.scala.ext.web.Router

import scala.concurrent.{Future, Promise}
import scala.util.{Failure, Success}
import cml.core.utils.NetworkConfiguration._
import io.vertx.core.logging.{Logger, LoggerFactory}

/**
  * This is abstract class implements RouterVerticle. In this way, each micro-services allowed to follow its path
  *
  * @author Chiara Volonnino
  */

abstract class RouterVerticle extends ScalaVerticle {

  var server: HttpServer = _

  private val log: Logger = LoggerFactory.getLogger("Router Verticle")

  override def startFuture(): Future[Unit] = {

    val promise = Promise[Unit]()
    val router = Router.router(vertx)
    initializeRouter(router)
    initializeService
    vertx.createHttpServer()
      .requestHandler(router.accept)
      .listenFuture(AuthenticationServicePort, ServiceHost)
      .onComplete({
        case Success(startedServer) =>
          log.info(s"Server successfully started on port: $AuthenticationServicePort", None)
          server = startedServer
          promise.success(())
        case Failure(ex) =>
          log.info(s"Server failed to start on port: $AuthenticationServicePort, b/c ${ex.getCause}", None)
          promise.failure(ex)
      })
    promise.future
  }

  override def stopFuture(): Future[Unit] = {
    log.info("Stopping", None)
    for {
      _ <- server.closeFuture()
      _ <- vertx.undeployFuture(this.deploymentID)
    } yield ()
  }
  /**
    * Initialize router
    *
    * @param router is router to initialize
    */
  def initializeRouter(router: Router): Unit

  /**
    * Initializes the services
    *
    * @return
    */
  def initializeService: Unit

}