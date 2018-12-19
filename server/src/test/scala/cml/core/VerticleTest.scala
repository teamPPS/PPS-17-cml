package cml.core


import io.vertx.lang.scala.ScalaVerticle

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.{Failure, Success}

/**
  * This trait utils for test. Deploy all verticle preset in a set and undeploy this with terminated test.
  *
  * @author Chiara Volonnino, ecavina
  */
trait VerticleTest {

  this: VertxTest =>

  private var servicesIdentifier: Set[String] = _

  /**
    * Deploys all the services passed as parameter.
    *
    * @param services the list of services to deploy
    */
  def deploy(services: List[ScalaVerticle]): Unit = {
    servicesIdentifier = Set()
    services.foreach(service => servicesIdentifier = servicesIdentifier + Await.result(vertx.deployVerticleFuture(service)
      .andThen {
        case Success(d) => d
        case Failure(t) => throw new RuntimeException(t)
      }, 10000.millis))
  }

  /**
    * Undeploys all the service previously deployed.
    *
    * @param atMost the maximum time we can wait for start services
    * @throws RuntimeException if any verticle can't be undeployed
    */
  def undeploy(atMost: Duration = 10000.millis): Unit = {
    servicesIdentifier.foreach(service => Await.result(vertx.undeployFuture(service), atMost))
  }
}
