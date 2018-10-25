package cml.core

import java.util.Base64

/**
  * This trait defines and manages tokens useful for authentication.
  *
  * @author Chiara Volonnino
  */


trait TokenAuthentication {

  /**
    * Create a header for http request
    *
    * @param username
    * @param password
    * @return header for a request or None
    */
  def base64Authentication(username: String, password: String): Option[String]

  /**
    * Check username and password for a request
    *
    * @param header
    * @return username and password or none
    */
  def checkBase64Authentication(header: String): Option[(String, String)]

  /**
    * Create a JWT header for http request
    * @param token
    * @return header or None
    */
  def authenticationToken(token: String):Option[String]
  /**
    * Check handler and return token for handler request
    * @param header for a request
    * @return token corresponds to the handler request
    */
  def checkAuthenticationToken(header: String): Option[String]
}

object TokenAuthentication extends TokenAuthentication {

  val BASE64 = "base64"

  override def base64Authentication(username: String, password: String): Option[String] = {
    for(
      checkUsername <- Option(username) if checkUsername.nonEmpty;
      checkPassword <- Option(password) if checkPassword.nonEmpty
    ) yield s"$BASE64" + Base64.getEncoder.encodeToString(s"$checkUsername:$checkPassword".getBytes())
  }

  override def checkBase64Authentication(header: String): Option[(String, String)] = {
    for (
      checkHeader <- Option(header) if checkHeader.nonEmpty;
      headerInfo = checkHeader.split(s"$BASE64")(1) if headerInfo.nonEmpty;
      headerDecode = new String(Base64.getDecoder.decode(headerInfo)).split(":")
    ) yield (headerDecode(0), headerDecode(1))
  }

  override def authenticationToken(token: String):Option[String] = {
    for (
      checkToken <- Option(token) if token.nonEmpty
    ) yield checkToken
  }

  override def checkAuthenticationToken(header: String): Option[String] = {
    for (
      checkHeader <- Option(header) if checkHeader.nonEmpty;
      token = header if checkHeader.nonEmpty
    ) yield token
  }
}
