package services

import play.api.libs.json._
import play.api.libs.functional.syntax._
import yjps.models.{GetCrypt2Request, GetCryptRequest, StoreCryptRequest}

object RequestParsers {

  implicit val storeCryptRequestReads: Reads[StoreCryptRequest] = (
    (JsPath \ "data").read[JsValue] and
      (JsPath \ "keyslots").read[Seq[String]]
    )(StoreCryptRequest)

  implicit val getCryptRequestReads: Reads[GetCryptRequest] = (
    (JsPath \ "data_id").read[String] and
      (JsPath \ "keyslot_id").read[String]
    )(GetCryptRequest)

  implicit val getCrypt2RequestReads: Reads[GetCrypt2Request] = new Reads[GetCrypt2Request] {

    override def reads(json: JsValue): JsResult[GetCrypt2Request] =
      (JsPath \ "data_id")
        .read[String]
        .reads(json)
        .map(GetCrypt2Request)
  }


}
