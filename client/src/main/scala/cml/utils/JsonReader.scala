package cml.utils

import play.api.libs.json.JsValue

/**
  * Utility to get information from a Json file
  * @author ecavina
  *
  * @tparam A json object representation
  * @tparam B field id representation
  */
trait JsonReader[A,B] {

  /**
    * Look up value of field from a json file
    * @param jsonFile json where try to looking for
    * @param fieldName key of the field to find
    * @return value of the @fieldName if found or empty
    */
  def readJsonField(jsonFile: A, fieldName: B): A

  /**
    * Look up fieldName in the current object and all descendant
    * @param jsonFile json where try to looking for
    * @param fieldName key of the field to find
    * @return list of founded value with the @fieldName
    */
  def recursiveReadJsonField(jsonFile: A, fieldName: B): Seq[A]

  /**
    * Check if a property exist in the json file with a field name
    * @param jsonFile json where try to looking for
    * @param fieldName key of the field to find
    * @return true if found or false
    */
  def jsonFieldExist(jsonFile: A, fieldName: B): Boolean
}

/**
  * Object implements JsonJsonReader[A,B]
  */
object JsonReader extends JsonReader[JsValue, String] {

   override def readJsonField(jsonFile: JsValue, fieldName: String): JsValue = (jsonFile \ fieldName).get

   override def recursiveReadJsonField(jsonFile: JsValue, fieldName: String): Seq[JsValue] = jsonFile \\ fieldName

   override def jsonFieldExist(jsonFile: JsValue, fieldName: String): Boolean =
    recursiveReadJsonField(jsonFile, fieldName).exists(_ => true)
}
