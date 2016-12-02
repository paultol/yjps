package models

import reactivemongo.bson.{BSONDocumentReader, BSONDocumentWriter, Macros}

/**
  * Created by iv on 12/1/16.
  */
case class Metadata(ownerId: String)


object MetaData {
  implicit val reader: BSONDocumentReader[Metadata] = Macros.reader[Metadata]
  implicit val writer: BSONDocumentWriter[Metadata] = Macros.writer[Metadata]
}
