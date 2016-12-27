package controllers

import play.api.libs.json._
import play.api.mvc._
import services.YjpsMongoData
import storage.MongoStorage
import yjps.models._


class CryptStorage extends Controller {

  import services.RequestParsers._
  import services.ResponseFormatters._
  import scala.concurrent.ExecutionContext.Implicits.global

  val dataAccess = YjpsMongoData(MongoStorage)

  def storeCrypt = Action.async(parse.json(storeCryptRequestReads)) { request =>
    val parsedbody: StoreCryptRequest = request.body
    dataAccess.storeObject(parsedbody)
      .map(yjpsResponse =>
        corsHeaders(
          Ok(Json.toJson(yjpsResponse)))
      )
  }


  def getCrypt = Action.async(parse.json(getCryptRequestReads)) { request =>
    val parsedBody: GetCryptRequest = request.body
    dataAccess.getObject(parsedBody)
      .map(yjpsResponse =>
        corsHeaders(
          Ok(Json.toJson(yjpsResponse)))
      )
  }

  def getCrypt2 = Action.async(parse.json(getCrypt2RequestReads)) { request =>
    val parsedBody: GetCrypt2Request = request.body
    dataAccess.getObject2(parsedBody)
      .map(yjpsResponse =>
        corsHeaders(
          Ok(Json.toJson(yjpsResponse)))
      )
  }

  def options = Action {
    corsHeaders(
      Ok("Options"))
  }

  private def corsHeaders(result: Result): Result = {
    result.withHeaders(
      ACCESS_CONTROL_ALLOW_ORIGIN -> "*",
      ACCESS_CONTROL_ALLOW_METHODS -> "GET, POST",
      ACCESS_CONTROL_ALLOW_HEADERS -> "Content-Type")
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

}
