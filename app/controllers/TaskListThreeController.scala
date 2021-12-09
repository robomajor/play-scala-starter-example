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

  def withJsonBody[A](f: A => Result)(implicit request: Request[AnyContent], reads: Reads[A]) = {
    request.body.asJson.map { body =>
      Json.fromJson[A](body) match {
        case JsSuccess(a, path) => f(a)
        case e@JsError(_) => Redirect(routes.TaskListThreeController.load)
      }
    }.getOrElse(routes.TaskListThreeController.load)
  }

  def withSessionUsername(f: String => Result)(implicit request: Request[AnyContent]) = {
    request.session.get("username").map(f).getOrElse(Ok(Json.toJson(Seq.empty[String])))
  }

  def validate = Action { implicit request =>
    withJsonBody[UserData] { ud =>
      if (TaskListInMemoryModel.validateUser(ud.username, ud.password)) {
        Ok(Json.toJson(true))
          .withSession("username" -> ud.username, "csrfToken" -> play.filters.csrf.CSRF.getToken.map(_.value).getOrElse(""))
      } else {
        Ok(Json.toJson(false))
      }
    }
  }

  def createUser = Action { implicit request =>
    withJsonBody[UserData] { ud =>
      if (TaskListInMemoryModel.createUser(ud.username, ud.password)) {
        Ok(Json.toJson(true))
          .withSession("username" -> ud.username, "csrfToken" -> play.filters.csrf.CSRF.getToken.map(_.value).getOrElse(""))
      } else {
        Ok(Json.toJson(false))
      }
    }
  }

  def taskList = Action { implicit request =>
    withSessionUsername { username =>
      Ok(Json.toJson(TaskListInMemoryModel.getTasks(username)))
    }
  }

  def addTask = Action { implicit request =>
    withSessionUsername { username =>
      withJsonBody[String] { task =>
        TaskListInMemoryModel.addTask(username, task);
        Ok(Json.toJson(true))
      }
    }
  }

  def delete = Action { implicit request =>
    withSessionUsername { username =>
      withJsonBody[Int] { index =>
        TaskListInMemoryModel.removeTask(username, index)
        Ok(Json.toJson(true))
      }
    }
  }

  def logout = Action { implicit request =>
    Ok(Json.toJson(true)).withSession(request.session - "username")
  }
}
