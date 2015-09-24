package controller

import skinny._
import skinny.controller.AssetsController

object Controllers {

  def mount(ctx: ServletContext): Unit = {
    //users.mount(ctx)
    cards.mount(ctx)
    root.mount(ctx)
    AssetsController.mount(ctx)
    gAuth.mount(ctx)
  }

  object root extends RootController with Routes {
    val indexUrl = get("/?")(index).as('index)
    val logoutUrl = post("/logout")(logout).as('logout)
    val cardsUrl = get("/cards/?")(cards).as('cards)
    val cardUrl = get("/cards/:id")(card).as('card)
    val evaluationPostUrl = post("/cards/:id")(evaluationPost).as('card)
  }

  // ユーザー情報が外から丸見えになるのはやばすぎるので無効化(adminだけいじれるように後ほど修正)
  /*object users extends _root_.controller.UsersController with Routes {
  }*/

  object cards extends _root_.controller.CardsController with Routes {
  }

  object gAuth extends SessionsController with Routes {
    val twitterLoginUrl = post("/auth/twitter")(loginRedirect).as('twitterLogin)
    val twitterLoginCallbackUrl = get("/auth/twitter/callback")(callback).as('twitterLogin)
  }

}
