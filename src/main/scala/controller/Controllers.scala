package controller

import skinny._
import skinny.controller.AssetsController

object Controllers {

  def mount(ctx: ServletContext): Unit = {
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
  }

  object cards extends _root_.controller.CardsController with Routes {
  }

  object gAuth extends SessionsController with Routes {
    val googleLoginUrl = post("/auth/google")(loginRedirect).as('googleLogin)
    val googleLoginCallbackUrl = get("/auth/google/callback")(callback).as('googleLogin)
  }

}
