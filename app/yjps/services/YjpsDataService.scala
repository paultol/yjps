package yjps.services

import yjps.models.{RetrieveRequest, StoreRequest, YjpsResponse}

import scala.concurrent.Future

/**
  * Created by iv on 12/2/16.
  */

trait YjpsDataService {

  def storeObject(toInsert: StoreRequest): Future[YjpsResponse]
  def retrieveObject(toRetrieve: RetrieveRequest): Future[YjpsResponse]
}
