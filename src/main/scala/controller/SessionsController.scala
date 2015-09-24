package controller

import skinny.controller.feature.TwitterLoginFeature

/**
 * 参考
 * http://skinny-framework.org/documentation/oauth.html
 * https://github.com/skinny-framework/skinny-framework/blob/master/example/src/main/scala/controller/GoogleController.scala
 */
class SessionsController extends ApplicationController with TwitterLoginFeature {

  // these env variables are expected by default
  // SKINNY_OAUTH1_CONSUMER_KEY_TWITTER
  // SKINNY_OAUTH1_CONSUMER_SECRET_TWITTER

  override def isLocalDebug = true

  override protected def saveAuthorizedUser(user: twitter4j.User): Unit = {
    session.setAttribute("user", user)
  }

  override protected def handleWhenLoginSucceeded(): Any = {
    println("hello" * 100)
    model.User.findByTwitterUser(session("user").asInstanceOf[twitter4j.User]) match {
      case Some(user) =>
        flash("info") = "ログインしました"
        session.remove("user")
        session.setAttribute("user_id", user.id)
        redirect302("/")
      case None =>
        redirect302("/signup")
    }
  }

  override protected def handleWhenLoginFailed(): Any = {
    flash("warn") = "ログインに失敗しました"
    redirect302("/")
  }

}
