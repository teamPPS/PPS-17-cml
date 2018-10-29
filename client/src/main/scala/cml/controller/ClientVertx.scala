package cml.controller

import akka.actor.{ActorRef, ActorSystem, Props}
import cml.controller.messages.AuthenticationResponse.{LoginFailure, LoginSuccess, RegisterFailure, RegisterSuccess}
import cml.utils.Configuration.{AuthenticationMsg, Connection}
import io.vertx.lang.scala.json.JsonObject
import io.vertx.scala.core.Vertx
import io.vertx.scala.ext.web.client.WebClient

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

/**
  * This trait describes the Vertx client
  * @author Monica Gondolini
  */
trait ClientVertx {

  /**
    * Requests the registration of a user into the system
    * @param username player's username
    * @param password player's password
    */
  def register(username: String, password: String): Unit

  /**
    * Requests the login of a user into the system
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

  def apply(controller: AuthenticationController): ClientVertx = new ClientVertxImpl(controller)

  /**
    * This class implements the Vertx Client
    * @param controller authentication view controller instance
    */
  private class ClientVertxImpl(controller: AuthenticationController) extends ClientVertx{

    var system = ActorSystem("mySystem")
    var authenticationActor: ActorRef = system.actorOf(Props(new AuthenticationActor(controller)), "authenticationActor")

    override def register(username: String, password: String): Unit = {
      println(s"sending registration request from username:$username with password:$password") //debug
      disableButtons(true)

      client.post(Connection.port, Connection.host, Connection.requestUri)
        .sendJsonObjectFuture(new JsonObject().put("username", username).put("password", password))
        .onComplete{
          case Success(result) => authenticationActor ! RegisterSuccess(AuthenticationMsg.registerSuccess)
            println("Success: "+result) //debug
          case Failure(cause) => authenticationActor ! RegisterFailure(AuthenticationMsg.registerFailure)
            println("Failure: "+cause) //debug
            disableButtons(false)
        }
    }

    override def login(username: String, password: String): Unit = {
      println(s"sending login request from username:$username with password:$password") //debug
      disableButtons(true)

      client.get(Connection.port, Connection.host, Connection.requestUri)
        .sendJsonObjectFuture(new JsonObject().put("username", username).put("password", password))
        .onComplete{
          case Success(result) => authenticationActor ! LoginSuccess(AuthenticationMsg.loginSuccess)
            println("Success: "+result)//debug
          case Failure(cause) => authenticationActor ! LoginFailure(AuthenticationMsg.loginFailure)
            println("Failure: "+cause)//debug
            disableButtons(false)
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

    override def logout(username: String): Unit = ???

    /**
      * Switches on and off GUI buttons
      * @param b boolean
      */
    def disableButtons(b: Boolean): Unit ={
      controller.registerBtn.setDisable(b)
      controller.loginBtn.setDisable(b)
    }
  }


}

