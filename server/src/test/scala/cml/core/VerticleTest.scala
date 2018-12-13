package cml.core


import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.core.Vertx

import scala.collection.mutable
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.{Failure, Success}

/**
  * This trait utils for test. Deploy all verticle preset in a set and undeploy this with terminated test.
  *
  * @author Chiara Volonnino
  */
trait VerticleTest {
  this: VertxTest =>

  private val vertx = Vertx.vertx()
  private var servicesIdentifier: mutable.Set[String] = _

  /**
    * Deploys all the services passed as parameter.
    *
    * @param services the list of services to deploy
    * @param atMost the maximum time we can wait for start services
    */
  def deploy(services: List[ScalaVerticle]): Unit = {
    services.foreach(s => {
      vertx.deployVerticle(s)
    })
    servicesIdentifier = vertx.deploymentIDs()
  }

  /**
    * Undeploys all the service previously deployed.
    *
    * @param atMost the maximum time we can wait for start services
    * @throws RuntimeException if any verticle can't be undeployed
    */
  def undeploy(atMost: Duration = 10000.millis): Unit = {
    servicesIdentifier.foreach(service => Await.result(vertx.undeployFuture(service)
      .andThen {
        case Success(serviceToUndeploy) => serviceToUndeploy
        case Failure(error) => throw new RuntimeException(error)
      }, atMost))
  }
}
