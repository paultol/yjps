package services

import play.api.libs.json.{JsPath, JsValue, Reads}
import play.api.libs.functional.syntax._
import yjps.models.{RetrieveRequest, StoreRequest}

object RequestParsers {

  implicit val storeRequestReads: Reads[StoreRequest] = (
    (JsPath \ "data").read[JsValue] and
      (JsPath \ "keyslots").read[Seq[String]]
    )(StoreRequest)

  implicit val retrieveRequestReads: Reads[RetrieveRequest] = (
    (JsPath \ "data_id").read[String] and
      (JsPath \ "keyslot_id").read[String]
    )(RetrieveRequest)

}
