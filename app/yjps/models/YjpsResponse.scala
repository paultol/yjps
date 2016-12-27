package yjps.models

import play.api.libs.json.JsValue


trait YjpsResponse

case class StoreCryptResponse(data_id: String, keyslot_ids: Seq[String]) extends YjpsResponse

case class GetCryptResponse(data: JsValue, keyslot: String) extends  YjpsResponse

case class ErrorResponse(errmsg: String) extends YjpsResponse

