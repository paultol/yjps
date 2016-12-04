package controllers

import play.api.data.Form
import play.api.libs.functional.FunctionalBuilder
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.mvc.{Action, Controller, Result}
import service.YjpsMongoData
import storage.MongoStorage
import yjps.models.{StoreRequest, StoreResponse, YjpsRequest}

import scala.concurrent.Future

/**
  * Created by iv on 11/30/16.
  */
object Data extends Controller {

  val dataAccess = YjpsMongoData(MongoStorage)

  implicit val storeRequestReads: Reads[StoreRequest] = (
    (JsPath \ "data").read[JsObject] and
      (JsPath \ "keyslots").read[Seq[String]]
    )(StoreRequest)

  implicit val storeResponseWrites: Writes[StoreResponse] = new Writes[StoreResponse] {
      def writes(storeResponse: StoreResponse) = Json.obj(
        "data_id" -> storeResponse.data_id,
        "keyslot_ids" -> storeResponse.keyslot_ids
      )
    }

  def store: Action[JsValue] = Action(parse.json) { request =>
    request.body
      .asOpt
      .map(dataAccess.insertObject)
      .map{storeResponseFut =>
        storeResponseFut
          .map(storeResponse =>
            Ok(storeResponse)
          )
      }
      .getOrElse(
        Future[Result](BadRequest)
      )
  }
}
