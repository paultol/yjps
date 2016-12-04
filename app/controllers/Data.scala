package controllers

import play.api.data.Form
import play.api.libs.functional.FunctionalBuilder
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.mvc.{Action, Controller}
import service.YjpsMongoData
import storage.MongoStorage
import yjps.models.{StoreRequest, YjpsRequest}

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

  def store = Action(parse.json) {request =>

    val = storeRequestReads.reads(request.body)
    dataAccess.insertObject()
  }
}
