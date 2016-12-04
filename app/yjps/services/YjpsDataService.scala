package yjps.services

import yjps.models.{StoreRequest, StoreResponse}

import scala.concurrent.Future

/**
  * Created by iv on 12/2/16.
  */

trait YjpsDataService {

  def storeObject(toInsert: StoreRequest): Future[StoreResponse]
}
