package integrationtest

import org.scalatra.test.scalatest._
import org.scalatest._
import skinny._
import skinny.test._
import org.joda.time._
import _root_.controller.Controllers
import model._

class RatingsController_IntegrationTestSpec extends ScalatraFlatSpec with SkinnyTestSupport with BeforeAndAfterAll with DBSettings {
  addFilter(Controllers.ratings, "/*")

  override def afterAll() {
    super.afterAll()
    Rating.deleteAll()
  }

  def newRating = FactoryGirl(Rating).create()

  it should "show ratings" in {
    get("/ratings") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/ratings/") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/ratings.json") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/ratings.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show a rating in detail" in {
    get(s"/ratings/${newRating.id}") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/ratings/${newRating.id}.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/ratings/${newRating.id}.json") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show new entry form" in {
    get(s"/ratings/new") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "create a rating" in {
    post(s"/ratings",
      "user_id" -> Long.MaxValue.toString(),
      "card_id" -> Long.MaxValue.toString(),
      "score" -> Int.MaxValue.toString(),
      "summary" -> "dummy",
      "description" -> "dummy") {
      logBodyUnless(403)
      status should equal(403)
    }

    withSession("csrf-token" -> "valid_token") {
      post(s"/ratings",
        "user_id" -> Long.MaxValue.toString(),
        "card_id" -> Long.MaxValue.toString(),
        "score" -> Int.MaxValue.toString(),
        "summary" -> "dummy",
        "description" -> "dummy",
        "csrf-token" -> "valid_token") {
        logBodyUnless(302)
        status should equal(302)
        val id = header("Location").split("/").last.toLong
        Rating.findById(id).isDefined should equal(true)
      }
    }
  }

  it should "show the edit form" in {
    get(s"/ratings/${newRating.id}/edit") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "update a rating" in {
    put(s"/ratings/${newRating.id}",
      "user_id" -> Long.MaxValue.toString(),
      "card_id" -> Long.MaxValue.toString(),
      "score" -> Int.MaxValue.toString(),
      "summary" -> "dummy",
      "description" -> "dummy") {
      logBodyUnless(403)
      status should equal(403)
    }

    withSession("csrf-token" -> "valid_token") {
      put(s"/ratings/${newRating.id}",
        "user_id" -> Long.MaxValue.toString(),
        "card_id" -> Long.MaxValue.toString(),
        "score" -> Int.MaxValue.toString(),
        "summary" -> "dummy",
        "description" -> "dummy",
        "csrf-token" -> "valid_token") {
        logBodyUnless(302)
        status should equal(302)
      }
    }
  }

  it should "delete a rating" in {
    delete(s"/ratings/${newRating.id}") {
      logBodyUnless(403)
      status should equal(403)
    }
    withSession("csrf-token" -> "valid_token") {
      delete(s"/ratings/${newRating.id}?csrf-token=valid_token") {
        logBodyUnless(200)
        status should equal(200)
      }
    }
  }

}
