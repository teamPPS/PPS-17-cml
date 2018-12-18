package cml.controller

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSelection}
import cml.controller.actor.utils.ViewMessage.ViewVillageMessage._
import cml.controller.actor.utils.ActorUtils.ActorPath.AuthenticationActorPath
import cml.controller.fx.VillageViewController
import cml.controller.messages.VillageRequest._
import cml.controller.messages.VillageResponse.{CreateVillageSuccess, VillageFailure}
import cml.services.village.VillageServiceVertx.VillageServiceVertxImpl
import javafx.application.Platform

import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.implicitConversions
import scala.util.{Failure, Success}


/**
  * Class that implements the actor which manages the village interaction with user
  * @author Monica Gondolini
  */
class VillageActor() extends Actor with ActorLogging{

  val villageVertx = VillageServiceVertxImpl()
  val authenticationActor: ActorSelection = context actorSelection AuthenticationActorPath

  override def receive: Receive = {

    case CreateVillage() =>
      val senderActor: ActorRef = sender
      villageVertx.createVillage().onComplete {
        case Success(httpResponse) =>
          httpResponse match {
            case "Not a valid request" => log.info("Failure create village")
            case _ => senderActor ! CreateVillageSuccess()
          }
        case Failure(exception) => senderActor ! VillageFailure(createFailure)
      }

    case EnterVillage(controller) =>
      villageVertx.enterVillage().onComplete {
        case Success(httpResponse) =>
          httpResponse match {
            case "Not a valid request" => log.info("Failure entering in village")
            case _ => Platform.runLater(() => controller.setGridAndHandlers(httpResponse))
          }
        case Failure(exception) => authenticationActor ! VillageFailure(enterFailure)
      }

    case UpdateVillage(update) =>
      villageVertx.updateVillage(update).onComplete {
        case Success(httpResponse) =>
          httpResponse match {
            case "Not a valid request" => log.info("Failure to update village")
            case _ => log.info("Update Done")
          }
        case Failure(exception) => log.error("exception", exception)
      }

    case SetUpdateVillage(update) =>
      villageVertx.setUpdateVillage(update).onComplete {
        case Success(httpResponse) =>
          httpResponse match {
            case "Not a valid request" => log.info("Failure to set update village")
            case _ => log.info("Update Set Done")
          }
        case Failure(exception) => log.error("exception", exception)
      }

    case DeleteVillage(controller) =>
      villageVertx.deleteVillageAndUser().onComplete {
        case Success(httpResponse) =>
          httpResponse match {
            case "Not a valid request" => log.info("Failure to delete village")
            case _ => log.info("Deletion Done")
              deleteSucceedOnGui(controller)
          }
        case Failure(exception) => log.error("exception", exception)
      }
  }

  def deleteSucceedOnGui(controller: VillageViewController): Unit = Platform.runLater(() => controller.openAuthenticationView())
}
