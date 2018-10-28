package cml.database

import com.mongodb.MongoCredential
import org.mongodb.scala.bson.collection.mutable.Document
import org.mongodb.scala.{Completed, FindObservable, MongoClient, MongoClientSettings, MongoCollection, MongoDatabase, Observable,Observer, ReadPreference, ServerAddress}
import org.mongodb.scala.connection.ClusterSettings
import com.mongodb.MongoCredential._
import java.util.logging.{Level, Logger}
import org.mongodb.scala.connection.{NettyStreamFactoryFactory,SslSettings}
import scala.collection.JavaConverters._


class DatabaseConnection {

    def main(args: Array[String]): Unit = {
      val mongoLogger: Logger = Logger.getLogger("com.mongodb")
      mongoLogger.setLevel(Level.SEVERE);
      val clusterSettings: ClusterSettings = ClusterSettings.builder().hosts(List(new ServerAddress("example.com:27345"), new ServerAddress("example.com:20026")).asJava).build()
      val user: String = "testuser"
      val databasename: String = "scalatest"
      val password: Array[Char] = "<enter-a-password>".toCharArray
      val credential: MongoCredential = createCredential(user, databasename, password)
      val settings: MongoClientSettings = MongoClientSettings.builder()
        .clusterSettings(clusterSettings).credentialList(List(credential,credential).asJava).sslSettings(SslSettings.builder().enabled(true).build())
        .streamFactoryFactory(NettyStreamFactoryFactory()).build()
      val mongoClient: MongoClient = MongoClient(settings)
      val database: MongoDatabase = mongoClient.getDatabase("scalatest")
      mongoClient.close()
    }

}
