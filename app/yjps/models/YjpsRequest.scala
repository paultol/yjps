package yjps.models

import play.api.libs.json.{JsObject, JsValue}


/**
  * Created by iv on 12/3/16.
  */
trait YjpsRequest

case class StoreRequest(data: JsObject, keyslots: Seq[String]) extends YjpsRequest
