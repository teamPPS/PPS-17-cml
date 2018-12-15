package cml.core

import io.vertx.lang.scala.VertxExecutionContext
import io.vertx.scala.core.Vertx
import org.scalatest.AsyncFunSuite

/**
  * This trait is utils for tasting services test. Its provides a vertx context
  *
  * @author Chiara Volonnino
  */

trait VertxTest extends AsyncFunSuite {
  private val vertxContext = Vertx.currentContext().getOrElse(Vertx.vertx.getOrCreateContext())

  protected val vertx: Vertx = vertxContext.owner()
  protected implicit val vertxExecutionContext: VertxExecutionContext = VertxExecutionContext(vertxContext)
}
