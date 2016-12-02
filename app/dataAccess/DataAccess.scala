package dataAccess

import com.typesafe.config.{Config, ConfigFactory}
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.api.{MongoConnection, MongoDriver}

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by iv on 12/1/16.
  */
object DataAccess extends DataAccess {

  import ExecutionContext.Implicits.global

  lazy val config = ConfigFactory.load()
  val driver = MongoDriver(config)
  val connections = driver.connection(List("localhost"))
  val collection: Future[BSONCollection]  = connections.database("database").map(_.collection("DataObject"))

}


sealed trait DataAccess {
  val config: Config
  val driver: MongoDriver
  val connections: MongoConnection
  def collection: Future[BSONCollection]

  def insertObject()
}
