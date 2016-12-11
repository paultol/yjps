package service

import java.util.Base64
import java.security.MessageDigest

import play.api.libs.json.{JsObject, JsValue}
import reactivemongo.bson.{BSONDocument, BSONDocumentWriter, BSONObjectID, BSONString, BSONValue}
import service.MongoStorable.Keyslot
import yjps.models.StoreRequest

/**
  * Created by iv on 12/4/16.
  */
case class MongoStorable(data_id: String, data: JsValue, keyslots: Seq[Keyslot])

object MongoStorable {

  case class Keyslot(id: String, keyslot: String)

  implicit val writeKeyslotSeq: BSONDocumentWriter[Seq[Keyslot]] = BSONDocumentWriter[Seq[Keyslot]] {
    keyslotSeq: Seq[Keyslot] =>
      BSONDocument(
        keyslotSeq.map {
          keyslot =>
            keyslot.id -> BSONString(keyslot.keyslot)
        }
      )
  }

  private def generateB64Id = Base64.getEncoder.encodeToString(
    MessageDigest.getInstance("SHA-512").digest(
      BSONObjectID.generate().valueAsArray
    )
  )

  def apply(request: StoreRequest): MongoStorable = {
    MongoStorable(
      generateB64Id,
      request.data,
      request.keyslots.map(key => Keyslot(generateB64Id, key))
    )
  }
}

