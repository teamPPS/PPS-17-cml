package cml.core

import io.vertx.lang.scala.ScalaVerticle
import org.scalatest._

/**
  * This trait is utils for services test. In this trait deploy all service to require for test, and the undeploy this.
  *
  * @author Chiara Volonnino
  */

trait BeforeAndAfterTest extends VerticleTest with BeforeAndAfter {

  this: VertxTest =>

  /**
    * It contains the list of service to deploy before each test.
    *
    * @return a Traversable containing all the services to deploy
    */
  protected def serviceList: Traversable[ScalaVerticle]
  protected val verticleToUse: Unit
  before{
    verticleToUse(verticleToUse) // ancora non ottimizzato
    deploy(serviceList)
  }

  after {
    undeploy()
  }
}
