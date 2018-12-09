package cml.controller

import akka.actor.{Actor, ActorRef, ActorSelection}
import cml.controller.actor.utils.ViewMessage.ViewVillageMessage._
import cml.controller.messages.VillageRequest._
import cml.controller.messages.VillageResponse.{CreateVillageSuccess, VillageFailure}
import cml.services.authentication.TokenStorage
import cml.services.village.VillageServiceVertx.VillageServiceVertxImpl
import javafx.application.Platform

import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.implicitConversions
import scala.util.{Failure, Success}


/**
  * Class that implements the actor which manages the village interaction with user
  * @author Monica Gondolini
  */
class VillageActor() extends Actor{

  val villageVertx = VillageServiceVertxImpl()
  val authenticationActor: ActorSelection = context.actorSelection("/user/AuthenticationActor")

  /**
    * @return village behaviour
    */
  override def receive: Receive = {

    case CreateVillage() =>
      val senderActor: ActorRef = sender
      villageVertx.createVillage().onComplete {
        case Success(httpResponse) =>
          httpResponse match {
            case "Not a valid request" => println("Failure create village")
            case _ => senderActor ! CreateVillageSuccess()
          }
        case Failure(exception) => senderActor ! VillageFailure(createFailure)
      }

    case EnterVillage(controller) =>
      villageVertx.enterVillage().onComplete {
        case Success(httpResponse) =>
          httpResponse match {
            case "Not a valid request" => println("Failure entering in village")
            case _ => Platform.runLater(() => controller.setGridAndHandlers(httpResponse))
          }
        case Failure(exception) => authenticationActor ! VillageFailure(enterFailure)
      }

    case UpdateVillage(update) =>
      villageVertx.updateVillage(update).onComplete {
        case Success(httpResponse) =>
          httpResponse match {
            case "Not a valid request" => println("Failure to update village")
            case _ => println("Update Done")
          }
        case Failure(exception) => println(exception)
      }

    case SetUpdateVillage(update) =>
      println("actor set update village msg")
      villageVertx.setUpdateVillage(update).onComplete {
        case Success(httpResponse) =>
          httpResponse match {
            case "Not a valid request" => println("Failure to set update village")
            case _ => println("Update Set Done")
          }
        case Failure(exception) => println(exception)
      }

    case DeleteVillage() => villageVertx.deleteVillageAndUser()
      .onComplete {
        case Success(httpResponse) => println(httpResponse)
        case Failure(exception) => println(exception)
      }
  }
}
