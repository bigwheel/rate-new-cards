package controller

import org.scalatest._
import skinny._
import skinny.test._
import org.joda.time._
import model._

// NOTICE before/after filters won't be executed by default
class UsersControllerSpec extends FunSpec with Matchers with BeforeAndAfterAll with DBSettings {

  override def afterAll() {
    super.afterAll()
    User.deleteAll()
  }

  def createMockController = new UsersController with MockController
  def newUser = FactoryGirl(User).create()

  describe("UsersController") {

    describe("shows users") {
      it("shows HTML response") {
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/users/index"))
        controller.contentType should equal("text/html; charset=utf-8")
      }

      it("shows JSON response") {
        implicit val format = Format.JSON
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/users/index"))
        controller.contentType should equal("application/json; charset=utf-8")
      }
    }

    describe("shows a user") {
      it("shows HTML response") {
        val user = newUser
        val controller = createMockController
        controller.showResource(user.id)
        controller.status should equal(200)
        controller.getFromRequestScope[User]("item") should equal(Some(user))
        controller.renderCall.map(_.path) should equal(Some("/users/show"))
      }
    }

    describe("shows new resource input form") {
      it("shows HTML response") {
        val controller = createMockController
        controller.newResource()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/users/new"))
      }
    }

    describe("creates a user") {
      it("succeeds with valid parameters") {
        val controller = createMockController
        controller.prepareParams(
          "user_name" -> "dummy",
          "o_auth_type" -> "dummy",
          "o_auth_id" -> "dummy",
          "is_admin" -> "true")
        controller.createResource()
        controller.status should equal(200)
      }

      it("fails with invalid parameters") {
        val controller = createMockController
        controller.prepareParams() // no parameters
        controller.createResource()
        controller.status should equal(400)
        controller.errorMessages.size should be >(0)
      }
    }

    it("shows a resource edit input form") {
      val user = newUser
      val controller = createMockController
      controller.editResource(user.id)
      controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/users/edit"))
    }

    it("updates a user") {
      val user = newUser
      val controller = createMockController
      controller.prepareParams(
        "user_name" -> "dummy",
        "o_auth_type" -> "dummy",
        "o_auth_id" -> "dummy",
        "is_admin" -> "true")
      controller.updateResource(user.id)
      controller.status should equal(200)
    }

    it("destroys a user") {
      val user = newUser
      val controller = createMockController
      controller.destroyResource(user.id)
      controller.status should equal(200)
    }

  }

}
