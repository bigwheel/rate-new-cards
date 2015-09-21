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
    val id = params.getAs[Long]("id") // ここにくる場合は必ずcardNameパラメータがあるはず
    Card.where('id -> id).apply().headOption match {
      case Some(card) =>
        set("card", card)
        render("/root/card")
      case None =>
        haltWithBody(404)
    }
  }

}
