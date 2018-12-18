package cml.services.authentication

/**
  * This interface utils for storage token in client side
  * @author ecavina
  */

trait TokenStorage {

  /**
    * Set a user's token
    * @param token token
    */
  def setUserJWTToken(token: String)

  /**
    * Get user's token
    * @return user's token
    */
  def getUserJWTToken: String
}

/**
  * Object implements TokenStorage
  */
object TokenStorage extends TokenStorage {

  private var userToken: String = _

  override def setUserJWTToken(token: String): Unit = userToken = token

  override def getUserJWTToken: String = userToken
}


