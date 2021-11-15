package controllers

import org.scalatestplus.play.{HtmlUnitFactory, OneBrowserPerSuite, PlaySpec}
import org.scalatestplus.play.guice.GuiceOneServerPerSuite

class TaskListControllerTest extends PlaySpec with GuiceOneServerPerSuite with OneBrowserPerSuite with HtmlUnitFactory {

  "Task List One Controller" must {
    "login and access functions" in {
      go to s"http://localhost:$port/loginOne"

      pageTitle mustBe "Login"

      find(cssSelector("h2")).isEmpty mustBe false
      find(cssSelector("h2")).foreach(e => e.text mustBe "Login")

      click on "username-login"
      textField("username-login").value = "Mark"
      click on "password-login"
      pwdField(id("password-login")).value = "pass"
      submit()

      eventually {
        pageTitle mustBe "Task List"
        find(cssSelector("h2")).isEmpty mustBe false
        find(cssSelector("h2")).foreach(e => e.text mustBe "Task List")
        findAll(cssSelector("li")).toList.map(_.text) mustBe List("Eat","Sleep","Code","Cry")
      }
    }

    "add task in" in {
      pageTitle mustBe "Task List"
      click on "newTask"
      textField("newTask").value = "test"
      click on "submit-task"

      eventually {
        pageTitle mustBe "Task List"
        findAll(cssSelector("li")).toList.map(_.text) mustBe List("test","Eat","Sleep","Code","Cry")
      }
    }

    "delete task" in {
      pageTitle mustBe "Task List"
      click on "delete-3"
      eventually {
        pageTitle mustBe "Task List"
        findAll(cssSelector("li")).toList.map(_.text) mustBe List("test","Eat","Sleep","Cry")
      }
    }

    "logout" in {
      pageTitle mustBe "Task List"
      click on "logout"
      eventually {
        pageTitle mustBe "Login"
      }
    }
  }
}
