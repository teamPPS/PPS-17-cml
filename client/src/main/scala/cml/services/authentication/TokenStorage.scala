package cml.services.authentication

trait TokenStorage {

  def setUserJWTToken(token: String)

  def getUserJWTToken:String
}

object TokenStorage extends TokenStorage {

  private var userToken: String = _

  override def setUserJWTToken(token: String): Unit = userToken = token

  override def getUserJWTToken: String = userToken
}


