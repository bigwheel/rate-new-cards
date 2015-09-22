package controller

import skinny.controller.feature.GoogleLoginFeature
import skinny.oauth2.client.google.GoogleUser

/**
 * 参考
 * http://skinny-framework.org/documentation/oauth.html
 * https://github.com/skinny-framework/skinny-framework/blob/master/example/src/main/scala/controller/GoogleController.scala
 */
class SessionsController extends ApplicationController with GoogleLoginFeature {

// these env variables are expected by default
// SKINNY_OAUTH2_CLIENT_ID_GOOGLE
// SKINNY_OAUTH2_CLIENT_SECRET_GOOGLE

  override protected def redirectURI: String = "http://localhost:8080/auth/google/callback"

  override protected def saveAuthorizedUser(user: GoogleUser): Unit = {
    session.setAttribute("user", user)
  }

  override protected def handleWhenLoginSucceeded(): Any = {
    flash("info") = "You have successfully logged in."
    flash += ("info" -> "You have successfully logged in.")
    println("hello")
    redirect302("/")
  }

  override protected def handleWhenLoginFailed(): Any = {
    flash("warn") = "Login failed. Please try again."
    println("hello2")
    redirect302("/login")
  }

}
