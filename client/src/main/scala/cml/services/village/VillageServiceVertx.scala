package cml.services.village

import akka.actor.ActorRef

trait VillageServiceVertx {

}

object VillageServiceVertx{

  def apply(actor: ActorRef): VillageServiceVertx = new VillageServiceVertxImpl(actor)

  case class VillageServiceVertxImpl(ref: ActorRef) extends VillageServiceVertx{
    
  }
}