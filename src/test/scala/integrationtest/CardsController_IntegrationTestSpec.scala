package integrationtest

import org.scalatra.test.scalatest._
import org.scalatest._
import skinny._
import skinny.test._
import org.joda.time._
import _root_.controller.Controllers
import model._

class CardsController_IntegrationTestSpec extends ScalatraFlatSpec with SkinnyTestSupport with BeforeAndAfterAll with DBSettings {
  addFilter(Controllers.cards, "/*")

  override def afterAll() {
    super.afterAll()
    Card.deleteAll()
  }

  def newCard = FactoryGirl(Card).create()

  it should "show cards" in {
    get("/resource/cards") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/resource/cards/") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/resource/cards.json") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/resource/cards.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show a card in detail" in {
    get(s"/resource/cards/${newCard.id}") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/resource/cards/${newCard.id}.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/resource/cards/${newCard.id}.json") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show new entry form" in {
    get(s"/resource/cards/new") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "create a card" in {
    post(s"/resource/cards",
      "name" -> "dummy",
      "card_number" -> "1",
      "image_url" -> "dummy") {
      logBodyUnless(403)
      status should equal(403)
    }

    withSession("csrf-token" -> "valid_token") {
      post(s"/resource/cards",
        "name" -> "dummy",
        "card_number" -> "1",
        "image_url" -> "dummy",
        "csrf-token" -> "valid_token") {
        logBodyUnless(302)
        status should equal(302)
        val id = header("Location").split("/").last.toLong
        Card.findById(id).isDefined should equal(true)
      }
    }
  }

  it should "show the edit form" in {
    get(s"/resource/cards/${newCard.id}/edit") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "update a card" in {
    put(s"/resource/cards/${newCard.id}",
      "name" -> "dummy",
      "card_number" -> "1",
      "image_url" -> "dummy") {
      logBodyUnless(403)
      status should equal(403)
    }

    withSession("csrf-token" -> "valid_token") {
      put(s"/resource/cards/${newCard.id}",
        "name" -> "dummy",
        "card_number" -> "1",
        "image_url" -> "dummy",
        "csrf-token" -> "valid_token") {
        logBodyUnless(302)
        status should equal(302)
      }
    }
  }

  it should "delete a card" in {
    delete(s"/resource/cards/${newCard.id}") {
      logBodyUnless(403)
      status should equal(403)
    }
    withSession("csrf-token" -> "valid_token") {
      delete(s"/resource/cards/${newCard.id}?csrf-token=valid_token") {
        logBodyUnless(200)
        status should equal(200)
      }
    }
  }

}
