package models

import org.scalatestplus.play.PlaySpec

class TaskListInMemoryModelTest extends PlaySpec {

  "TaskListInMemoryModel" must {
    "do valid login for default user" in {
      TaskListInMemoryModel.validateUser("Mark", "pass") mustBe true
    }
    "reject login with wrong password" in {
      TaskListInMemoryModel.validateUser("Mark", "password") mustBe false
    }
    "reject login with wrong username and password" in {
      TaskListInMemoryModel.validateUser("Mike", "passwoed") mustBe false
    }
    "get correct default tasks" in {
     TaskListInMemoryModel.getTasks("Mark") mustBe List("Eat","Sleep","Code","Cry")
    }
    "create new user with no tasks" in {
      TaskListInMemoryModel.createUser("Gary", "password") mustBe true
      TaskListInMemoryModel.getTasks("Gary") mustBe Nil
    }
    "create new user with existing name" in {
      TaskListInMemoryModel.createUser("Mark", "password") mustBe false
    }
    "add new task for default user" in {
      TaskListInMemoryModel.addTask("Mark", "Wank")
      TaskListInMemoryModel.getTasks("Mark") must contain("Wank")
    }
    "add new task for new user" in {
      TaskListInMemoryModel.addTask("Gary", "Wank")
      TaskListInMemoryModel.getTasks("Gary") must contain("Wank")
    }
    "remove task from default user" in {
      TaskListInMemoryModel.removeTask("Mark", TaskListInMemoryModel.getTasks("Mark").indexOf("Eat"))
      TaskListInMemoryModel.getTasks("Mark") must not contain "Eat"
    }
  }
}
