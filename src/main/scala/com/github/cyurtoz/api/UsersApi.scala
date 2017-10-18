package com.github.cyurtoz.api

import akka.http.scaladsl.server.Directives._
import com.github.cyurtoz.utils.BearerTokenGenerator

trait UsersApi extends BearerTokenGenerator {
  val usersRoutes =
    (path("users" / "authentication") & post) {
      complete(generateSHAToken("InstagramPicsFilter"))
    }
}
