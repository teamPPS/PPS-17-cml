package cml.services.authentication

import akka.actor.ActorRef
import cml.controller.messages.AuthenticationResponse.{LoginFailure, LoginSuccess, RegisterFailure, RegisterSuccess}
import cml.core.TokenAuthentication
import cml.services.authentication.utils.AuthenticationUrl.AuthenticationUrl._
import cml.utils.Configuration.AuthenticationMsg
import cml.core.utils.NetworkConfiguration._
import io.netty.handler.codec.http.HttpHeaderNames
import io.vertx.scala.core.Vertx
import io.vertx.scala.ext.web.client.WebClient
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

/**
  * This trait describes the Vertx client
  * @author Monica Gondolini, Filippo Portolani
  * @author (modified by) Chiara Volonnino
  */
trait AuthenticationServiceVertx {

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
    * @param token token to delete
    */
  def logout(token: String) : Unit

  /**
    * Requests the deletion of a user from the system
    * @param username player's username
    */
  def delete(username: String): Unit

  def validationToken(token: String): Unit
}

/**
  * Companion object
  */
object AuthenticationServiceVertx{

  var vertx: Vertx = Vertx.vertx()
  var client: WebClient = WebClient.create(vertx)

  def apply(actor: ActorRef): AuthenticationServiceVertx = AuthenticationServiceVertxImpl(actor)

  /**
    * This class implements the Vertx Client
    * @param actor the actor i want to send messages to
    */
  case class AuthenticationServiceVertxImpl(actor: ActorRef) extends AuthenticationServiceVertx{

    override def register(username: String, password: String): Unit = {
      println(s"sending registration request from username:$username with password:$password")
      client.post(AuthenticationServicePort, ServiceHost, RegisterApi)
        .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), TokenAuthentication.base64Authentication(username, password).get)
        .sendFuture
        .onComplete{
          case Success(result) =>
            actor ! RegisterSuccess("token")
            println("Success: " +result) //debug
          case Failure(cause) => actor ! RegisterFailure(AuthenticationMsg.registerFailure)
            println("Failure: " +cause) //debug
        }
    }

    override def login(username: String, password: String): Unit = {
      println(s"sending login request from username:$username with password:$password") //debug
      client.put(AuthenticationServicePort, ServiceHost, LoginApi)
        .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), TokenAuthentication.base64Authentication(username, password).get)
        .sendFuture
        .onComplete{
          case Success(result) => actor ! LoginSuccess("token")
            println("Success: " + result)//debug
          case Failure(cause) => actor ! LoginFailure(AuthenticationMsg.loginFailure)
            println("Failure: " + cause)//debug
        }
    }

    override def logout(token: String): Unit = {
      println(s"sending logout request with token: $token")
      client.delete(AuthenticationServicePort, ServiceHost, DeleteApi)
        .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), TokenAuthentication.authenticationToken(token).get)
        .sendFuture
        .onComplete{
          case Success(result) => println("Success: " + result)//debug
          case Failure(cause) => println("Failure: " + cause)//debug
        }
    }

    override def delete(username: String): Unit = {
      client.delete(AuthenticationServicePort, ServiceHost, LogoutApi) // add token?
        .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), TokenAuthentication.authenticationToken(username).get)
        .sendFuture
        .onComplete{
          case Success(result) => println("Success: " + result)//debug
          case Failure(cause) => println("Failure: " + cause)//debug
        }
    }

    override def validationToken(token: String): Unit = {
      client.get(AuthenticationServicePort, ServiceHost, ValidationTokenApi)
        .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), token)
        .sendFuture
    }
  }
}

