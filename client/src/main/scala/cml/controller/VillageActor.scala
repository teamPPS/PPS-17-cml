package cml.controller

import akka.actor.{Actor, ActorRef}
import cml.controller.actor.utils.ViewMessage.ViewVillageMessage._
import cml.controller.messages.VillageRequest.{CreateVillage, DeleteVillage, EnterVillage, UpdateVillage}
import cml.controller.messages.VillageResponse.{CreateVillageSuccess, EnterVillageSuccess, VillageFailure}
import cml.services.village.VillageServiceVertx.VillageServiceVertxImpl

import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.implicitConversions
import scala.util.{Failure, Success}


/**
  * Class that implements the actor which manages the village interaction with user
  * @author Monica Gondolini
  */
class VillageActor() extends Actor{

  val villageVertx = VillageServiceVertxImpl()
  /**
    * @return village behaviour
    */
  override def receive: Receive = {

    case CreateVillage() =>
      val senderActor: ActorRef = sender
      villageVertx.createVillage().onComplete {
        case Success(httpResponse) =>
          httpResponse match {
            case "Not a valid request" => senderActor ! VillageFailure(createFailure)
            case _ => senderActor ! CreateVillageSuccess()
          }
        case Failure(exception) => senderActor ! VillageFailure(createFailure)
      }

    case EnterVillage(controller) =>
      val senderActor: ActorRef = sender
      villageVertx.enterVillage().onComplete {
        case Success(httpResponse) =>
          httpResponse match {
            case "Not a valid request" => senderActor ! VillageFailure(enterFailure)
            case _ => controller.setGridAndHandlers() //popolare model + metodo inizializzazione
          }
        case Failure(exception) => senderActor ! VillageFailure(enterFailure)
      }

    case UpdateVillage(update) => villageVertx.updateVillage(update)
      .onComplete {
        case Success(httpResponse) => println(httpResponse) //modificare model: Passo textarea(?) e model nel messaggio UpdateVillage
        case Failure(exception) => println(exception) //visualizza cose nella gui -> altro attore con controller? Passo textarea e model nel messaggio dall'handler
      }

    case DeleteVillage() => villageVertx.deleteVillageAndUser()
      .onComplete {
        case Success(httpResponse) => println(httpResponse) //cancella tutto torna alla schermata di autenticazione
        case Failure(exception) => println(exception) // visualizza cose nella gui -> altro attore con controller? Passo textarea e model nel messaggio dall'handler
      }
  }
}
