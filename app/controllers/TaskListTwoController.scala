package controllers

import models.TaskListInMemoryModel

import javax.inject._
import play.api.mvc._

class TaskListTwoController @Inject() (cc: ControllerComponents) (implicit assetsFinder: AssetsFinder) extends AbstractController(cc) {

  def load = Action { implicit request =>
    Ok(views.html.version2Main())
  }

  def login = Action {
    Ok(views.html.login2())
  }
}
