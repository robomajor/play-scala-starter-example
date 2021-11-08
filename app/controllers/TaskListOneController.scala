package controllers

import models.TaskListInMemoryModel

import javax.inject._
import play.api.mvc._

@Singleton
class TaskListOneController @Inject() (cc: ControllerComponents) (implicit assetsFinder: AssetsFinder) extends AbstractController(cc) {

  def login = Action { implicit request =>
    Ok(views.html.loginOne())
  }

  def validateLoginGet(username: String, password: String) = Action {
    Ok(s"$username logged in")
  }

  def validateLoginPost = Action { implicit request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("username").head
      val password = args("password").head
      if (TaskListInMemoryModel.validateUser(username, password)) {
        Redirect(routes.TaskListOneController.taskList).withSession("username" -> username)
      } else Redirect(routes.TaskListOneController.login).flashing("error" -> "Invalid username/password combination")
    }.getOrElse(Redirect(routes.TaskListOneController.login))
  }

  def createUser = Action { implicit request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("username").head
      val password = args("password").head
      if (TaskListInMemoryModel.createUser(username, password)) {
        Redirect(routes.TaskListOneController.taskList).withSession("username" -> username)
      } else Redirect(routes.TaskListOneController.login).flashing("error" -> "User creation failed")
    }.getOrElse(Redirect(routes.TaskListOneController.login))
  }

  def taskList = Action { implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      val tasks = TaskListInMemoryModel.getTasks(username)
      Ok(views.html.taskListOne(tasks))
    }.getOrElse(Redirect(routes.TaskListOneController.login))
  }

  def logout = Action {
    Redirect(routes.TaskListOneController.login).withNewSession
  }
}
