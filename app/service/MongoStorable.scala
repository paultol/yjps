package service

import java.security.MessageDigest
import java.util.Base64

import play.api.libs.json.JsValue
import reactivemongo.bson.BSONObjectID
import yjps.models.StoreRequest

/**
  * Created by iv on 12/4/16.
  */
case class MongoStorable(data_id: String, data: JsValue, keyslots: Seq[(String, String)])

object MongoStorable {

  private def generateB64Id = Base64.getEncoder.encodeToString(
    MessageDigest.getInstance("SHA-512").digest(
      BSONObjectID.generate().valueAsArray
    )
  )

  def apply(request: StoreRequest): MongoStorable = {
    MongoStorable(
      generateB64Id,
      request.data,
      request.keyslots.map(key => (generateB64Id, key))
    )
  }
}

