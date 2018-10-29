package cml.controller

import cml.utils.Configuration.{AuthenticationMsg, Connection}
import io.vertx.lang.scala.json.JsonObject
import io.vertx.scala.core.Vertx
import io.vertx.scala.ext.web.client.WebClient
import javafx.application.Platform

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

trait ClientVertx {

  def register (username: String, password: String): Unit

  def login (username: String, password: String): Future[String]

  def logout(username: String) : Future[Unit]

  def delete (username: String): Future[Unit]

}

object ClientVertx{

  var vertx: Vertx = Vertx.vertx()
  var client: WebClient = WebClient.create(vertx)


  def apply(controller: AuthenticationController): ClientVertx = new ClientVertxImpl(controller)
  private class ClientVertxImpl(controller: AuthenticationController) extends ClientVertx{

    override def register(username: String, password: String): Unit = {
      client.post(Connection.port, Connection.host, Connection.requestUri)
        .sendJsonObjectFuture(new JsonObject().put("username", username).put("password", password))
        .onComplete{
          case Success(result) =>
            println(result)
            Platform.runLater(() => controller.formMsgLabel.setText(AuthenticationMsg.loginSuccess))
          //sostituire la view con quella del villaggio mandando un messaggio all'attore del villaggio (?)
          case Failure(cause) =>
            println("Failure")
            //Response msg

              Platform.runLater(() => controller.formMsgLabel.setText(AuthenticationMsg.loginFailure))
          //      disableButtons(false)
        }
    }

    override def login(username: String, password: String): Future[String] = ???

    override def delete(username: String): Future[Unit] = ???

    override def logout(username: String): Future[Unit] = ???
  }
}

