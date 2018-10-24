package cml.server

import io.vertx.lang.scala.ScalaVerticle

class ScalaExampleVerticle extends ScalaVerticle {

  override def start(): Unit = {
    println("starting...")
  }

  override def stop(): Unit = {
    println("stopping...")
  }
}
