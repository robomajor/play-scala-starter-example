package controllers

import javax.inject._
import play.api.mvc._
import play.api.i18n._

@Singleton
class TaskListOneController @Inject() (cc: ControllerComponents) (implicit assetsFinder: AssetsFinder) extends AbstractController(cc) {

  def login = Action {
    Ok(views.html.loginOne())
  }

  def validateLoginGet(username: String, password: String) = Action {
    Ok(s"$username logged in")
  }

  def validateLoginPost = Action { request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("username").head
      val password = args("password").head
      Redirect(routes.TaskListOneController.taskList)
    }.getOrElse(Redirect(routes.TaskListOneController.login))
  }

  def taskList = Action {
    val tasks = List("task_1", "task_2", "task_3", "task_4")

    Ok(views.html.taskListOne(tasks))
  }
}
