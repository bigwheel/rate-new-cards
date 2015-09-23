package controller

import skinny.controller.feature.TwitterLoginFeature
import twitter4j.User

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

  override protected def saveAuthorizedUser(user: User): Unit = {
    session.setAttribute("user", user)
  }

  override protected def handleWhenLoginSucceeded(): Any = {
    flash("info") = "ログインしました"
    redirect302("/")
  }

  override protected def handleWhenLoginFailed(): Any = {
    flash("warn") = "ログインに失敗しました"
    redirect302("/")
  }

}
