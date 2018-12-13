package cml.services.authentication

import cml.core.TokenAuthentication
import cml.core.utils.NetworkConfiguration._
import cml.services.authentication.utils.AuthenticationUrl._
import io.netty.handler.codec.http.{HttpHeaderNames, HttpResponseStatus}
import io.vertx.core.logging.{Logger, LoggerFactory}
import io.vertx.scala.core.Vertx
import io.vertx.scala.ext.web.client.WebClient

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
    * Check username and then valid a token
    * @param header to check
    * @return a future if header found
    */
  def validationToken(header: String): Future[Unit]

  /**
    * Deletion of a user from the system
    * @return success if deletion is completed
    */
  def delete(): Future[String]
}

/**
  * Companion object
  */
object AuthenticationServiceVertx{

  val vertx: Vertx = Vertx.vertx()
  var client: WebClient = WebClient.create(vertx)

  val successfulRegisterResponse: Int = HttpResponseStatus.CREATED.code
  val successfulLoginResponse: Int = HttpResponseStatus.OK.code
  val successfulDeletionResponse: Int = HttpResponseStatus.OK.code

  val log: Logger = LoggerFactory.getLogger("Authentication Service")

  def apply(): AuthenticationServiceVertx = AuthenticationServiceVertxImpl()

  /**
    * This class implements the Vertx Client
    */
  case class AuthenticationServiceVertxImpl() extends AuthenticationServiceVertx {

    override def register(username: String, password: String): Future[String] = {
      log.info("sending registration request from username:%s", username)
      client.post(AuthenticationServicePort, ServiceHostForRequest, RegisterApi)
        .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), TokenAuthentication.base64Authentication(username, password).get)
        .sendFuture
        .map(r => r.statusCode match { // technical debt?
          case `successfulRegisterResponse` => r.bodyAsString().getOrElse("")
          case _ => "Not a valid request"
        })
    }

    override def login(username: String, password: String): Future[String] = {
      log.info("sending login request from username:%s", username)
      client.put(AuthenticationServicePort, ServiceHostForRequest, LoginApi)
        .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), TokenAuthentication.base64Authentication(username, password).get)
        .sendFuture
        .map(r => r.statusCode match { //technical debt?
          case `successfulLoginResponse` => r.bodyAsString().getOrElse("")
          case _ => "Not a valid request"
        })
    }

    override def logout(token: String): Future[Unit] = {
      log.info("sending logout request with token: %s", token)
      client.delete(AuthenticationServicePort, ServiceHostForRequest, LogoutApi)
        .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), TokenAuthentication.authenticationToken(token).get)
        .sendFuture
        .map(_ => ())
    }

    override def validationToken(header: String): Future[Unit] = {
      client.get(AuthenticationServicePort, ServiceHostForRequest, ValidationTokenApi)
        .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), header)
        .sendFuture
        .map(response => response.statusCode match {
          case `successfulLoginResponse` => Future.successful(successfulLoginResponse)
          case _ => Future.failed(new Exception)
        })
    }

    override def delete(): Future[String] = {
    log.info("sending deletion request")
    client.put(AuthenticationServicePort, ServiceHostForRequest, LoginApi)
      .putHeader(HttpHeaderNames.AUTHORIZATION.toString(), TokenStorage.getUserJWTToken)
      .sendFuture
      .map(r => r.statusCode match {
        case `successfulDeletionResponse` => r.bodyAsString().getOrElse("")
        case _ => "Not a valid request"
      })
  }
  }
}