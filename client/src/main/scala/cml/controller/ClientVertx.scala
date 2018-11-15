package cml.controller

import akka.actor.ActorRef
import cml.controller.messages.AuthenticationResponse.{LoginFailure, LoginSuccess, RegisterFailure, RegisterSuccess}
import cml.core.TokenAuthentication
import cml.utils.Configuration.{AuthenticationMsg, Connection}
import io.netty.handler.codec.http.HttpHeaderNames
import io.vertx.lang.scala.json.JsonObject
import io.vertx.scala.core.Vertx
import io.vertx.scala.ext.web.client.WebClient

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

/**
  * This trait describes the Vertx client
  * @author Monica Gondolini
  * @author (edited by)  Chiara Volonnino, Filippo Portolani
  */
trait ClientVertx {

  /**
    * Requests a user registration into the system
    * @param username player's username
    * @param password player's password
    */
  def register(username: String, password: String): Unit

  /**
    * Requests the login of a specific user into the system
    * @param username player's username
    * @param password player's password
    */
  def login(username: String, password: String): Unit

  /**
    * Requests the logout of a user from the system
    * @param username player's username
    */
  def logout(username: String) : Unit

  /**
    * Requests the deletion of a user from the system
    * @param username player's username
    */
  def delete(username: String): Unit
}

/**
  * Companion object
  */
object ClientVertx{

  var vertx: Vertx = Vertx.vertx()
  var client: WebClient = WebClient.create(vertx)

  def apply(actor: ActorRef): ClientVertx = ClientVertxImpl(actor)

  /**
    * This class implements the Vertx Client
    * @param actor the actor i want to send messages to
    */
  case class ClientVertxImpl(actor: ActorRef) extends ClientVertx{

    override def register(username: String, password: String): Unit = {
      println(s"sending registration request from username:$username with password:$password") //debug
      val header = TokenAuthentication.base64Authentication(username, password)
      client.post(Connection.port, Connection.host, Connection.requestUri)
        .putHeader(HttpHeaderNames.AUTHORIZATION.toString(),header.get)
        .sendFuture
        .onComplete{
          case Success(result) => actor ! RegisterSuccess(AuthenticationMsg.registerSuccess)
            println("Success: "+result) //debug
          case Failure(cause) => actor ! RegisterFailure(AuthenticationMsg.registerFailure)
            println("Failure: "+cause) //debug
        }
    }

    override def login(username: String, password: String): Unit = {
      println(s"sending login request from username:$username with password:$password") //debug
      val header = TokenAuthentication.base64Authentication(username, password)
      client.put(Connection.port, Connection.host, Connection.requestUri)
        .putHeader(HttpHeaderNames.AUTHORIZATION.toString(),header.get)
        .sendFuture
        .onComplete{
          case Success(result) => actor ! LoginSuccess(AuthenticationMsg.loginSuccess)
            println("Success: "+result)//debug
          case Failure(cause) => actor ! LoginFailure(AuthenticationMsg.loginFailure)
            println("Failure: "+cause)//debug
        }
    }

    override def delete(username: String): Unit = {
      client.delete(Connection.port, Connection.host, Connection.requestUri)
        .sendJsonObjectFuture(new JsonObject().put("username", username))
        .onComplete{
          case Success(result) => println("Success: "+result)//debug
          case Failure(cause) => println("Failure: "+cause)//debug
        }
    }

    override def logout(username: String): Unit = {
      client.put(Connection.port, Connection.host, Connection.requestUri)
        .sendJsonObjectFuture(new JsonObject().put("username", username))
        .onComplete {
          case Success(result) => println("Success: " + result) //debug
          case Failure(cause) => println("Failure: " + cause) //debug
        }
    }
  }
}

