package controller

import skinny._
import skinny.controller.AssetsController

object Controllers {

  def mount(ctx: ServletContext): Unit = {
    cards.mount(ctx)
    root.mount(ctx)
    AssetsController.mount(ctx)
  }

  object root extends RootController with Routes {
    val indexUrl = get("/?")(index).as('index)
    val loginUrl = get("/login")(login).as('login)
    val cardsUrl = get("/cards/?")(cards).as('cards)
    val cardUrl = get("/cards/:id")(card).as('card)
  }
  object cards extends _root_.controller.CardsController with Routes {
  }

}
