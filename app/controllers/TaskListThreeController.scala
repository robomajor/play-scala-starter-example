package controllers

import models._

import javax.inject._
import play.api.mvc._
import play.api.libs.json._

@Singleton
class TaskListThreeController  @Inject() (cc: ControllerComponents) (implicit assetsFinder: AssetsFinder) extends AbstractController(cc) {

  def load = Action { implicit request =>
    Ok(views.html.version3Main())
  }

  implicit val userDataReads = Json.reads[UserData]

  def validate = Action { implicit request =>
    request.body.asJson.map { body =>
      Json.fromJson[UserData](body) match {
        case JsSuccess(ud, path) =>
          if (TaskListInMemoryModel.validateUser(ud.username, ud.password)) {
            Ok(Json.toJson(true))
              .withSession("username" -> ud.username, "csrfToken" -> play.filters.csrf.CSRF.getToken.get.value)
          } else Ok(Json.toJson(false))
        case e @ JsError(_) => Redirect(routes.TaskListThreeController.load)
      }
    }.getOrElse(Redirect(routes.TaskListThreeController.load))
  }

  def taskList = Action { implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      Ok(Json.toJson(TaskListInMemoryModel.getTasks(username)))
    }.getOrElse(Ok(Json.toJson(Seq.empty[String])))
  }

  def addTask = Action { implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      request.body.asJson.map { body =>
        Json.fromJson[String](body) match {
          case JsSuccess(task, path) =>
            TaskListInMemoryModel.addTask(username, task)
            Ok(Json.toJson(true))
          case e @ JsError(_) => Redirect(routes.TaskListThreeController.load)
        }
      }.getOrElse(Ok(Json.toJson(false)))
    }.getOrElse(Ok(Json.toJson(false)))
  }

}
