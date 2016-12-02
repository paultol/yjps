package models

import dataAccess.Request
import reactivemongo.bson.{BSONDocumentWriter, Macros}

/**
  * Created by iv on 12/1/16.
  */
abstract class YjpsKey

case class StoreKey(ephemeral_key: String, encrypted_body: String, encrypted_keys: Seq[String]) extends YjpsKey

case class RetrieveKey(data_id: Long, key_ids: Seq[Long]) extends YjpsKey

case class DeleteKey(data_id: Long) extends YjpsKey

