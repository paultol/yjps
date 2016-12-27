package yjps.services

import yjps.models.{GetCrypt2Request, GetCryptRequest, StoreCryptRequest, YjpsResponse}

import scala.concurrent.Future

trait YjpsDataService {

  def storeObject(toInsert: StoreCryptRequest): Future[YjpsResponse]
  def getObject(toRetrieve: GetCryptRequest): Future[YjpsResponse]
  def getObject2(toRetrieve: GetCrypt2Request): Future[YjpsResponse]
}
