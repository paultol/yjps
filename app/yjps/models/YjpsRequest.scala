package yjps.models

import play.api.libs.json.{JsObject, JsValue}


/**
  * Created by iv on 12/3/16.
  */
trait YjpsRequest

case class StoreRequest(data: JsValue, keyslots: Seq[String]) extends YjpsRequest

case class RetrieveRequest(data_id: String, keyslot_id: String) extends YjpsRequest
