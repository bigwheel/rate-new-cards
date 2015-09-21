package controller

import model.Card
import skinny._

class RootController extends ApplicationController {

  def index = render("/root/index")

  def cards = {
    set("cards", Card.findAllModels())
    render("/root/cards")
  }

  def card = {
    val name = params.get("cardName").get // ここにくる場合は必ずcardNameパラメータがあるはず
    Card.where('name -> name).apply().headOption match {
      case Some(card) =>
        set("card", card)
        render("/root/card")
      case None =>
        haltWithBody(404)
    }
  }

}
