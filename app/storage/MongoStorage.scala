package storage

import com.typesafe.config.ConfigFactory
import reactivemongo.api.{MongoConnection, MongoDriver}

/**
  * Created by iv on 12/4/16.
  */
trait MongoStorage {

  val connections: MongoConnection
}

object MongoStorage extends MongoStorage {

  lazy val config = ConfigFactory.load()
  val driver = MongoDriver(config)
  override val connections = driver.connection(List("localhost"))
}


