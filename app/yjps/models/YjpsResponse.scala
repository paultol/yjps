package yjps.models

import play.api.libs.json.JsValue


trait YjpsResponse

case class StoreResponse(data_id: String, keyslot_ids: Seq[String]) extends YjpsResponse

case class ErrorResponse(errmsg: String) extends YjpsResponse

case class RetrieveResponse(data: JsValue, keyslot: String) extends  YjpsResponse
