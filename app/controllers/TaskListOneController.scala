package controllers

import models.TaskListInMemoryModel

import javax.inject._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

case class LoginData(username: String, password: String)

@Singleton
class TaskListOneController @Inject() (cc: MessagesControllerComponents) (implicit assetsFinder: AssetsFinder) extends MessagesAbstractController(cc) {

  val loginForm = Form(mapping(
    "Username" -> text(3, 10),
    "Password" -> text(4)
  )(LoginData.apply)(LoginData.unapply))

  def login = Action { implicit request =>
    Ok(views.html.loginOne(loginForm))
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

  def createUserForm = Action { implicit request =>
    loginForm.bindFromRequest().fold(
      formWithErrors => BadRequest(views.html.loginOne(formWithErrors)),
      loginData =>
        if (TaskListInMemoryModel.createUser(loginData.username, loginData.password)) {
          Redirect(routes.TaskListOneController.taskList).withSession("username" -> loginData.username)
        } else Redirect(routes.TaskListOneController.login).flashing("error" -> "User creation failed")
    )
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

  def addTask = Action { implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      val postVals = request.body.asFormUrlEncoded
      postVals.map { args =>
        val task = args("newTask").head
        TaskListInMemoryModel.addTask(username, task)
        Redirect(routes.TaskListOneController.taskList)
      }.getOrElse(Redirect(routes.TaskListOneController.taskList))
    }.getOrElse(Redirect(routes.TaskListOneController.login))
  }

  def deleteTask = Action { implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      val postVals = request.body.asFormUrlEncoded
      postVals.map { args =>
        val index = args("index").head.toInt
        TaskListInMemoryModel.removeTask(username, index)
        Redirect(routes.TaskListOneController.taskList)
      }.getOrElse(Redirect(routes.TaskListOneController.taskList))
    }.getOrElse(Redirect(routes.TaskListOneController.login))
  }
}
