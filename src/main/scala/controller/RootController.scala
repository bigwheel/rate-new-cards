package controller

import model.Card
import skinny._

class RootController extends ApplicationController {

  before() { set("userOption", session.get("user")) }

  def index = render("/root/index")

  def logout = {
    session.clear
    flash("info") = "ログアウトしました"
    redirect302("/")
  }

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

  def evaluationPost = {
    redirect302(request.getRequestURI)
  }
}
