package cml.services.authentication

import akka.actor.ActorRef
import cml.core.TokenAuthentication
import cml.services.authentication.utils.AuthenticationUrl._
import cml.core.utils.NetworkConfiguration._
import cml.services.ClientVertx
import io.netty.handler.codec.http.{HttpHeaderNames, HttpResponseStatus}
import io.vertx.scala.core.Vertx
import io.vertx.scala.ext.web.client.{HttpRequest, WebClient}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


/**
  * This trait describes the Vertx client
  * @author Monica Gondolini
  * @author (edited by) Chiara Volonnino, Filippo Portolani
  */
trait AuthenticationServiceVertx {

  /**
    * Requests a user registration into the system
    * @param username player's username
    * @param password player's password
    * @return future if register completes successfully, otherwise it fails.
    */
  def register(username: String, password: String): Future[String]

  /**
    * Requests the login of a specific user into the system
    * @param username player's username
    * @param password player's password
    * @return future if login completes successfully, otherwise it fails.
    */
  def login(username: String, password: String): Future[String]

  /**
    * Requests the logout of a user from the system
    * @param token token to delete
    * @return future if logout completes successfully, otherwise it fails.
    */
  def logout(token: String) : Future[Unit]

  /**
    * Requests the deletion of a user from the system
    * @param token token with match request
    * @return future if delete completes successfully, otherwise it fails.
    */
  def delete(token: String): Future[Unit]

  /**
    * Check username and then valid a token
    * @param header to check
    * @return a future if header found
    */
  def validationToken(header: String): Future[Unit]
}

/**
  * Companion object
  */
object AuthenticationServiceVertx extends ClientVertx {

//  val vertx: Vertx = Vertx.vertx()
//  var client: WebClient = WebClient.create(vertx)
  var client: WebClient = authenticationClient
  val successfulRegisterResponse: Int = HttpResponseStatus.CREATED.code
  val successfulLoginResponse: Int = HttpResponseStatus.OK.code

  def apply(actor: ActorRef): AuthenticationServiceVertx = AuthenticationServiceVertxImpl(actor)

  /**
    * This class implements the Vertx Client
    * @param actor the actor i want to send messages to
    */
  case class AuthenticationServiceVertxImpl(actor: ActorRef) extends AuthenticationServiceVertx {

    override def register(username: String, password: String): Future[String] = {
      println(s"sending registration request from username:$username with password:$password")
      client.post(AuthenticationServicePort, ServiceHostForRequest, RegisterApi)
        .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), TokenAuthentication.base64Authentication(username, password).get)
        .sendFuture
        .map(r => r.statusCode match { // technical debt?
          case `successfulRegisterResponse` => r.bodyAsString().getOrElse("")
          case _ => "Not a valid request"
        })
    }

    override def login(username: String, password: String): Future[String] = {
      println(s"sending login request from username:$username with password:$password") //debug
      client.put(AuthenticationServicePort, ServiceHostForRequest, LoginApi)
        .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), TokenAuthentication.base64Authentication(username, password).get)
        .sendFuture
        .map(r => r.statusCode match { //technical debt?
          case `successfulLoginResponse` => r.bodyAsString().getOrElse("")
          case _ => "Not a valid request"
        })
    }

    override def logout(token: String): Future[Unit] = {
      println(s"sending logout request with token: $token")
      client.delete(AuthenticationServicePort, ServiceHost, DeleteApi)
        .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), TokenAuthentication.authenticationToken(token).get)
        .sendFuture
        .map(() => _)
    }

    override def delete(token: String): Future[Unit] = {
      client.delete(AuthenticationServicePort, ServiceHost, LogoutApi) // add token?
        .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), TokenAuthentication.authenticationToken(token).get)
        .sendFuture
        .map(()=>_)
    }

    override def validationToken(header: String): Future[Unit] = {
      client.get(AuthenticationServicePort, ServiceHost, ValidationTokenApi)
        .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), header)
        .sendFuture
        .map(() => _)
    }
  }
}