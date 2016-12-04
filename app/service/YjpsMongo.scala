package service

import com.typesafe.config.ConfigFactory
import reactivemongo.api.MongoDriver
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.{BSONDocumentWriter, BSONObjectID}
import yjps.models.{ErrorResponse, StoreRequest, StoreResponse, YjpsResponse}
import yjps.services.YjpsDataService

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by iv on 12/1/16.
  */
case class YjpsMongo(configPath: Option[String]) extends YjpsDataService {
  import ExecutionContext.Implicits.global

  lazy val config = ConfigFactory.load()
  val driver = MongoDriver(config)
  val connections = driver.connection(List("localhost"))
  def collection: Future[BSONCollection]  = connections.database("database").map(_.collection("data_objects"))

  implicit val writeMongoStorable = BSONDocumentWriter[MongoStorable]

  override def insertObject(toInsert: StoreRequest): Future[YjpsResponse] =  {
    val storable = MongoStorable(toInsert)
    collection.flatMap(_.insert(storable)).map { res =>
      if (res.ok) StoreResponse(storable.data_id, storable.keyslots.map(_.id))
      else ErrorResponse("Failed to store data")
    }
  }
}




