package yjps.models

import play.api.libs.json.{JsObject, JsValue}


/**
  * Created by iv on 12/3/16.
  */
trait YjpsRequest

case class StoreCryptRequest(data: JsValue, keyslots: Seq[String]) extends YjpsRequest

case class GetCryptRequest(data_id: String, keyslot_id: String) extends YjpsRequest

case class GetCrypt2Request(data_id: String) extends YjpsRequest
