package services

import play.api.libs.json.JsObject
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter, BSONString}
import reactivemongo.play.json._
import storage.MongoStorage
import yjps.models._
import yjps.services.YjpsDataService

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by iv on 12/1/16.
  */
case class YjpsMongoData(mongodb: MongoStorage) extends YjpsDataService {
  import ExecutionContext.Implicits.global

  def collection: Future[BSONCollection]  = mongodb.connections.database("database").map(_.collection("data_objects"))

  implicit val writeMongoStorable: BSONDocumentWriter[MongoStorable] = BSONDocumentWriter[MongoStorable] {
    mongoStorable: MongoStorable =>
      BSONDocument(
        Seq(
          "data_id" -> BSONString(mongoStorable.data_id),
          "data" -> JsObjectWriter.write(mongoStorable.data.as[JsObject]),
          "keyslots" -> BSONDocument(mongoStorable.keyslots.map{case (k, v) => (k, BSONString(v))})
        )
      )
  }

  override def storeObject(toInsert: StoreRequest): Future[YjpsResponse] = {
    val storable = MongoStorable(toInsert)
    collection.flatMap(_.insert(storable)).map { res =>
      if (res.ok) StoreResponse(storable.data_id, storable.keyslots.map(_._1))
      else ErrorResponse("Failed to store data")
    }
  }

  implicit val readRetrieveRequest: BSONDocumentReader[MongoStorable] = BSONDocumentReader[MongoStorable] {
    doc: BSONDocument =>
      MongoStorable(
        doc.getAs[String]("data_id").get,
        doc.getAs[BSONDocument]("data")
          .map(bson =>
            BSONFormats.BSONDocumentFormat.writes(bson))
          .get,
        {
          val keyslotDoc = doc.getAs[BSONDocument]("keyslots").get
          val keys = keyslotDoc.elements.map{ case (k, _) => k }
          keys.foldLeft[Seq[(String, String)]] (Seq()) { case (acc, key) =>
            keyslotDoc.getAs[String](key)
              .map{ v =>
                (key, v) +: acc
              }
                .getOrElse(acc)
          }
        }
      )
  }

  override def retrieveObject(toRetrieve: RetrieveRequest): Future[YjpsResponse] = {
    val query = BSONDocument("data_id" -> toRetrieve.data_id)

    for {
      col <- collection
      resultOpt<- col.find(query).one[MongoStorable]
    } yield {
      resultOpt.flatMap { storable =>
        storable.keyslots.toMap.get(toRetrieve.keyslot_id)
          .map { keyslot =>
            RetrieveResponse(storable.data, keyslot)
          }
      }.getOrElse(ErrorResponse("Failed to retrieve data"))
    }

  }
}




