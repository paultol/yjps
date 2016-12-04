package yjps.models

/**
  * Created by iv on 12/3/16.
  */
trait YjpsResponse

case class StoreResponse(data_id: String, keyslot_ids: Seq[String]) extends YjpsResponse

case class ErrorResponse(errmsg: String) extends YjpsResponse
