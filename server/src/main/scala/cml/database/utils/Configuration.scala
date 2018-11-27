package cml.database.utils

/**
  * This object contains all the Server configurations
  * @author MonicaGondolini
  */

object Configuration{

  object DbConfig{
    val uri: String = "mongodb://admin:cmlPPS17@ds239903.mlab.com:39903/cml-storage"
    val dbName: String = "cml-storage"
    val usersColl: String = "Users"
    val villageColl: String = "Village"
  }

  object DocumentNotFoundException{
    val DocumentNotFoundException: String = "DocumentNotFoundException"
  }
}
