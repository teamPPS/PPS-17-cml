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

  val villageVertx = VillageServiceVertxImpl(self)

  /**
    * @return village behaviour
    */
  override def receive: Receive = {

    case CreateVillage() => villageVertx.createVillage()
      .onComplete {
        case Success(httpResponse) =>
          httpResponse match {
            case "Not a valid request" => sender ! VillageFailure(createFailure)
            case _ => sender ! CreateVillageSuccess()
              println("Success")
          }
        case Failure(exception) => sender ! VillageFailure(createFailure)
      }

    case EnterVillage() => villageVertx.enterVillage()
      .onComplete {
        case Success(httpResponse) =>
          httpResponse match {
            case "Not a valid request" => sender ! VillageFailure(enterFailure)
            case _ => sender ! EnterVillageSuccess()
              println("Success")
          }
        case Failure(exception) => sender ! VillageFailure(enterFailure)
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

/*  def checkResponse(str: String, sender: ActorRef, m: String): Unit = str match { //technical debt?
    case "Not a valid request" => sender ! VillageFailure(m)
    case _ => sender !
      println("Success this is server response with the token: " + str)
  }*/
}
