package service

import java.util.Base64
import java.security.MessageDigest

import play.api.libs.json.JsObject
import reactivemongo.bson.{BSONDocumentWriter, BSONObjectID}
import service.MongoStorable.Keyslot
import yjps.models.StoreRequest

/**
  * Created by iv on 12/4/16.
  */
case class MongoStorable(data_id: String, data: JsObject, keyslots: Seq[Keyslot])

object MongoStorable {

  case class Keyslot(id: String, keyslot: String)

  implicit val writeKeyslot = BSONDocumentWriter[Keyslot]

  private def generateB64Id = Base64.getEncoder.encodeToString(
    MessageDigest.getInstance("SHA-512").digest(
      BSONObjectID.generate().valueAsArray
    )
  )

  def apply(request: StoreRequest) = {
    MongoStorable(
      generateB64Id,
      request.data,
      request.keyslots.map(key => Keyslot(generateB64Id, key))
    )
  }
}

