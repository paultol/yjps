package controllers

import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.mvc.{Action, Controller, Result}
import service.YjpsMongoData
import storage.MongoStorage
import yjps.models._

import scala.concurrent.Future

/**
  * Created by iv on 11/30/16.
  */
class Data extends Controller {

  import scala.concurrent.ExecutionContext.Implicits.global

  val dataAccess = YjpsMongoData(MongoStorage)

  implicit val storeRequestReads: Reads[StoreRequest] = (
    (JsPath \ "data").read[JsObject] and
      (JsPath \ "keyslots").read[Seq[String]]
    )(StoreRequest)

  implicit val yjpsResponseWrites: Writes[YjpsResponse] = new Writes[YjpsResponse] {
      def writes(yjpsResponse: YjpsResponse) = yjpsResponse match {
        case StoreResponse(data_id, keyslot_ids) =>
          Json.obj(
            "data_id" -> data_id,
            "keyslot_ids" -> keyslot_ids
          )
        case ErrorResponse(errmsg) =>
          Json.obj(
            "error_message" -> errmsg
          )
      }
    }

  def store: Action[JsValue] = Action.async(parse.json) { request =>
    request.body
      .asOpt
      .map(dataAccess.storeObject)
      .map{yjpsResponseFut =>
        yjpsResponseFut
          .map(yjpsResponse =>
            Ok(Json.toJson(yjpsResponse))
          )
      }.getOrElse(
        Future[Result](BadRequest)
      )
  }
}
