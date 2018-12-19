package cml.core.utils

import io.vertx.core.json.JsonObject
import pdi.jwt.{Jwt, JwtAlgorithm, JwtClaim}
import scala.util.{Failure, Success}

/**
  * This object describes basic functions of standards JWT
  *
  * @author Chiara Volonnino
  */
object JWTAuthentication {

  val Username: String = "username"
  private val secretKey = "secretKey"
  private val withAlgorithm = JwtAlgorithm.HS256

  /**
    * Create a token from a claim
    *
    * @param claim is the text use to create a token
    * @return token for the request
    */
  def encodeToken(claim: JwtClaim): Option[String] = claim match {
    case null => None
    case _ => Some(Jwt.encode(claim, secretKey, withAlgorithm))
  }

  /**
    * Decode existent token
    *
    * @param token is the token to decode
    * @return the content of token decoded
    */
  def decodeToken(token: String): Option[JwtClaim] = {
    Jwt.decodeRawAll(token, secretKey, Seq(withAlgorithm)) match {
      case Success((_, b, _)) =>
        Some(JwtClaim(b))
      case Failure(_) => None
    }
  }

  /**
    * Validate a token
    *
    * @param token token to validate
    * @return true if token is valid, false in opposite case
    */
  def validateToken(token: String): Boolean = {
    Jwt.isValid(token, secretKey, Seq(withAlgorithm))
  }

  /**
    * Create a token by username
    *
    * @param username username to encode
    * @return encode token
    */
  def encodeUsernameToken(username: String): Option[String] = username match {
    case null => None
    case _ => encodeToken(JwtClaim(new JsonObject().put(Username, username).encode()))
  }

  /**
    * Decode username token
    *
    * @param token token to decode
    * @return username
    */
  def decodeUsernameToken(token: String): Option[String] = {
    decodeToken(token).map(decoded => new JsonObject(decoded.content).getString(Username))
  }
}
