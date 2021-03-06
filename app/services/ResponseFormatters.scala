package services

import play.api.libs.json.{Json, Writes}
import yjps.models.{ErrorResponse, GetCryptResponse, StoreCryptResponse, YjpsResponse}

/**
  * Created by iv on 12/24/16.
  */
object ResponseFormatters {

  implicit val yjpsResponseWrites: Writes[YjpsResponse] = new Writes[YjpsResponse] {
    def writes(yjpsResponse: YjpsResponse) = yjpsResponse match {
      case StoreCryptResponse(data_id, keyslot_ids) =>
        Json.obj(
          "data_id" -> data_id,
          "keyslot_ids" -> keyslot_ids
        )
      case GetCryptResponse(data, keyslot) =>
        Json.obj(
          "data" -> data,
          "keyslot" -> keyslot
        )
      case ErrorResponse(errmsg) =>
        Json.obj(
          "error_message" -> errmsg
        )
    }
  }

}
