package services

import storage.MongoStorage
import yjps.services.YjpsTransactionService

/**
  * Created by iv on 12/4/16.
  */
case class YjpsMongoTransaction(mongodb: MongoStorage) extends YjpsTransactionService {

}
