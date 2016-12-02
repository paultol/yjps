package models

import play.libs.Json

/**
  * Created by iv on 12/1/16.
  */
trait YjpsObject

case class YjpsFullObject(ephemeral_key: String,
                          encrypted_body: String,
                          encrypted_keys: Seq[String],
                          meta: Metadata) extends YjpsObject

case class YjpsCryptoObject(ephemeral_key: String,
                            encrypted_body: String,
                            encrypted_keys: Seq[String]) extends YjpsObject

case class YjpsNoKeyObject(ephemeral_key: String,
                           encrypted_body: String,
                           encrypted_keys: String) extends YjpsObject

case class YjpsObjectMetadata(metadata: Metadata) extends YjpsObject
