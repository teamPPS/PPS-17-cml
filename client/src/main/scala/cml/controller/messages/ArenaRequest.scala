package cml.controller.messages


/**
  * Arena request message
  *
  * @author Chiara Volonnino
  */
object ArenaRequest {

  sealed trait ArenaRequest

  case class ExitRequest() extends ArenaRequest

}
