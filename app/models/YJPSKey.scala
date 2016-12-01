package models

import DataAccess.Request

/**
  * Created by iv on 12/1/16.
  */
class YJPSKey extends Request {

}

case class StoreKey(ephemeral_key: String, encrypted_body: String, encrypted_keys: Seq[String])

case class RetrieveKey(data_id: Long, key_ids: Seq[Long])

case class DeleteKey(data_id: Long)
