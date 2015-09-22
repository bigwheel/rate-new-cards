import controller.ApplicationController
import skinny.controller.feature.GoogleLoginFeature
import skinny.oauth2.client.google.GoogleUser
import skinny._

class SessionsController extends ApplicationController with GoogleLoginFeature {

// these env variables are expected by default
// SKINNY_OAUTH2_CLIENT_ID_GOOGLE
// SKINNY_OAUTH2_CLIENT_SECRET_GOOGLE

  override protected def redirectURI: String = "http://localhost:8080/auth/google/callback"

  override protected def saveAuthorizedUser(gUser: GoogleUser): Unit = {
    val user = User.findById(gUser.id).getOrElse {
      User.create(gUser)
    }
    session.setAttribute("currentUser", user)
  }

  override protected def handleWhenLoginFailed(): Any = {
    flash("warn") = "Login failed. Please try again."
    flash += ("warni" -> "Login failed. Please try again.")
    redirect302("/login")
  }

  override protected def handleWhenLoginSucceeded(): Any = {
    flash("info") = "You have successfully registered and logged in."
    redirect302("/")
  }
}

object Controllers {
  object gAuth extends SessionsController with Routes {
    val facebookLoginUrl = post("/auth/google")(loginRedirect).as('googleLogin)
    val facebookLoginCallbackUrl = get("/auth/google/callback")(callback).as('googleLogin)
  }
}
