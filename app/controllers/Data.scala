package controllers

import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.mvc._
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
    (JsPath \ "data").read[JsValue] and
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

  /** testing comes here **/

  case class StringObject(string: String)

  implicit val stringObjectReads: Reads[StringObject] =
    (JsPath \ "string").read[String].map(StringObject)

  implicit val stringObjectWrites: Writes[StringObject] = new Writes[StringObject] {
    def writes(stringObject: StringObject) = Json.obj(
      "string" -> stringObject.string
    )
  }

  def test = Action(parse.json(stringObjectReads)) { request =>
    println(request.body)
    Ok(Json.toJson(request.body)).withHeaders(
      ACCESS_CONTROL_ALLOW_ORIGIN -> "*",
      ACCESS_CONTROL_ALLOW_METHODS -> "GET, POST",
      ACCESS_CONTROL_ALLOW_HEADERS -> "Content-Type")
  }

  /** testing ends here **/

  def store = Action.async(parse.json(storeRequestReads)) { request =>
    val parsedbody: StoreRequest = request.body
    println(parsedbody)
    dataAccess.storeObject(parsedbody)
      .map(yjpsResponse =>
        Ok(Json.toJson(yjpsResponse)).withHeaders(
          ACCESS_CONTROL_ALLOW_ORIGIN -> "*",
          ACCESS_CONTROL_ALLOW_METHODS -> "GET, POST",
          ACCESS_CONTROL_ALLOW_HEADERS -> "Content-Type")
        )
  }

  def options = Action {
    Ok("yoyoyoyo here are your options")
        .withHeaders(
          ACCESS_CONTROL_ALLOW_ORIGIN -> "*",
          ACCESS_CONTROL_ALLOW_METHODS -> "GET, POST",
          ACCESS_CONTROL_ALLOW_HEADERS -> "Content-Type")
  }
}
