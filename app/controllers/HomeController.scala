package controllers

import javax.inject._

import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) (implicit assetsFinder: AssetsFinder)
  extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action { implicit request =>
    Ok(views.html.index("This is start page for test scala project"))
  }

  def product(prodType: String, prodNum: Int) = Action {
    Ok(s"Product type is: $prodType, product number is $prodNum")
  }

  def randomNumber = Action {
    Ok(util.Random.nextInt(100).toString)
  }

  def randomString(length: Int) = Action {
    Ok(util.Random.nextString(length))
  }
}
