package cml.controller.messages


/**
  * Arena request message
  *
  * @author Chiara Volonnino
  */
object ArenaRequest {

  sealed trait ArenaRequest

  case class HelloChallenger(msg: String) extends ArenaRequest

  /**
    * Request for stopping Actor
    */
  case class StopRequest() extends ArenaRequest


}
