package cml.controller.messages

object ArenaResponse {

  sealed trait ArenaResponse

  case class HelloChallengerSuccess() extends ArenaResponse
}
