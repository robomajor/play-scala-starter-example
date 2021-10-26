package controllers

import javax.inject._
import play.api.mvc._
import play.api.i18n._

@Singleton
class TaskListOneController @Inject() (cc: ControllerComponents) (implicit assetsFinder: AssetsFinder) extends AbstractController(cc) {

  def taskList = Action {
    val tasks = List("task_1", "task_2", "task_3", "task_4")

    Ok(views.html.taskListOne(tasks))
  }
}
