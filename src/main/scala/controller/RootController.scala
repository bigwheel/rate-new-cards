package controller

import model.Card
import skinny._

class RootController extends ApplicationController {

  before() { set("userIdOption", session.get("user_id").map(_.asInstanceOf[Long])) }

  def index = render("/root/index")

  def scoreSample = render("/root/score_sample")

  def signup = {
    set("user", session("user"))
    render("/root/signup")
  }

  def signupPost = {
    val twitterUser = session("user").asInstanceOf[twitter4j.User]
    model.User.createWithAttributes(
      'userName -> formParams.apply("displayName"),
      'oAuthType -> "twitter",
      'oAuthId -> twitterUser.getId.toString,
      'isAdmin -> false
    )
    flash("info") = "アカウントの作成が完了しました"

    println("a" * 1000)
    session.remove("user")
    session.setAttribute("user_id", model.User.findByTwitterUser(twitterUser).get.id)
    redirect302("/")
  }

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
    flash("info") = "投稿に成功しました"
    redirect302(request.getRequestURI)
  }
}
