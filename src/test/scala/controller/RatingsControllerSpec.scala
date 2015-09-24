package controller

import org.scalatest._
import skinny._
import skinny.test._
import org.joda.time._
import model._

// NOTICE before/after filters won't be executed by default
class RatingsControllerSpec extends FunSpec with Matchers with BeforeAndAfterAll with DBSettings {

  override def afterAll() {
    super.afterAll()
    Rating.deleteAll()
  }

  def createMockController = new RatingsController with MockController
  def newRating = FactoryGirl(Rating).create()

  describe("RatingsController") {

    describe("shows ratings") {
      it("shows HTML response") {
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/ratings/index"))
        controller.contentType should equal("text/html; charset=utf-8")
      }

      it("shows JSON response") {
        implicit val format = Format.JSON
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/ratings/index"))
        controller.contentType should equal("application/json; charset=utf-8")
      }
    }

    describe("shows a rating") {
      it("shows HTML response") {
        val rating = newRating
        val controller = createMockController
        controller.showResource(rating.id)
        controller.status should equal(200)
        controller.getFromRequestScope[Rating]("item") should equal(Some(rating))
        controller.renderCall.map(_.path) should equal(Some("/ratings/show"))
      }
    }

    describe("shows new resource input form") {
      it("shows HTML response") {
        val controller = createMockController
        controller.newResource()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/ratings/new"))
      }
    }

    describe("creates a rating") {
      it("succeeds with valid parameters") {
        val controller = createMockController
        controller.prepareParams(
          "user_id" -> Long.MaxValue.toString(),
          "card_id" -> Long.MaxValue.toString(),
          "score" -> Int.MaxValue.toString(),
          "summary" -> "dummy",
          "description" -> "dummy")
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
      val rating = newRating
      val controller = createMockController
      controller.editResource(rating.id)
      controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/ratings/edit"))
    }

    it("updates a rating") {
      val rating = newRating
      val controller = createMockController
      controller.prepareParams(
        "user_id" -> Long.MaxValue.toString(),
        "card_id" -> Long.MaxValue.toString(),
        "score" -> Int.MaxValue.toString(),
        "summary" -> "dummy",
        "description" -> "dummy")
      controller.updateResource(rating.id)
      controller.status should equal(200)
    }

    it("destroys a rating") {
      val rating = newRating
      val controller = createMockController
      controller.destroyResource(rating.id)
      controller.status should equal(200)
    }

  }

}
