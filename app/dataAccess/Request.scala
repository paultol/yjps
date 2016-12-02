package models

import dataAccess.Insertable

/**
  * Created by iv on 12/1/16.
  */
trait Request

case class StoreRequest(ephemeral_key: String,
                        encrypted_body: String,
                        encrypted_keys: Seq[String]) extends Request
  with Insertable

case class RetrieveRequest(data_id: Long, key_ids: Seq[Long]) extends Request

case class DeleteRequest(data_id: Long) extends Request
