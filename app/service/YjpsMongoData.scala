package service

import play.api.libs.json.{JsObject, JsValue}
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.play.json._
import reactivemongo.bson.{BSONDocument, BSONDocumentWriter, BSONString}
import storage.MongoStorage
import yjps.models.{ErrorResponse, StoreRequest, StoreResponse, YjpsResponse}
import yjps.services.YjpsDataService

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by iv on 12/1/16.
  */
case class YjpsMongoData(mongodb: MongoStorage) extends YjpsDataService {
  import ExecutionContext.Implicits.global
  import MongoStorable._

  def collection: Future[BSONCollection]  = mongodb.connections.database("database").map(_.collection("data_objects"))

  implicit val writeMongoStorable: BSONDocumentWriter[MongoStorable] = BSONDocumentWriter[MongoStorable] {
    mongoStorable: MongoStorable =>
      BSONDocument(
        Seq(
          "data_id" -> BSONString(mongoStorable.data_id),
          "data" -> JsObjectWriter.write(mongoStorable.data.as[JsObject]),
          "keyslots" -> writeKeyslotSeq.write(mongoStorable.keyslots)
        )
      )
  }

  override def storeObject(toInsert: StoreRequest): Future[YjpsResponse] =  {
    val storable = MongoStorable(toInsert)
    collection.flatMap(_.insert(storable)).map { res =>
      if (res.ok) StoreResponse(storable.data_id, storable.keyslots.map(_.id))
      else ErrorResponse("Failed to store data")
    }
  }
}




