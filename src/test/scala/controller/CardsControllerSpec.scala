package controller

import org.scalatest._
import skinny._
import skinny.test._
import org.joda.time._
import model._

// NOTICE before/after filters won't be executed by default
class CardsControllerSpec extends FunSpec with Matchers with BeforeAndAfterAll with DBSettings {

  override def afterAll() {
    super.afterAll()
    Card.deleteAll()
  }

  def createMockController = new CardsController with MockController
  def newCard = FactoryGirl(Card).create()

  describe("CardsController") {

    describe("shows cards") {
      it("shows HTML response") {
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/cards/index"))
        controller.contentType should equal("text/html; charset=utf-8")
      }

      it("shows JSON response") {
        implicit val format = Format.JSON
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/cards/index"))
        controller.contentType should equal("application/json; charset=utf-8")
      }
    }

    describe("shows a card") {
      it("shows HTML response") {
        val card = newCard
        val controller = createMockController
        controller.showResource(card.id)
        controller.status should equal(200)
        controller.getFromRequestScope[Card]("item") should equal(Some(card))
        controller.renderCall.map(_.path) should equal(Some("/cards/show"))
      }
    }

    describe("shows new resource input form") {
      it("shows HTML response") {
        val controller = createMockController
        controller.newResource()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/cards/new"))
      }
    }

    describe("creates a card") {
      it("succeeds with valid parameters") {
        val controller = createMockController
        controller.prepareParams(
          "name" -> "dummy",
          "image_url" -> "dummy")
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
      val card = newCard
      val controller = createMockController
      controller.editResource(card.id)
      controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/cards/edit"))
    }

    it("updates a card") {
      val card = newCard
      val controller = createMockController
      controller.prepareParams(
        "name" -> "dummy",
        "image_url" -> "dummy")
      controller.updateResource(card.id)
      controller.status should equal(200)
    }

    it("destroys a card") {
      val card = newCard
      val controller = createMockController
      controller.destroyResource(card.id)
      controller.status should equal(200)
    }

  }

}
