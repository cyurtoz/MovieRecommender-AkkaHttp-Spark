package com.github.cyurtoz.api

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.directives.ParameterDirectives.ParamMagnet
import com.github.cyurtoz.model.MovieService
import com.github.cyurtoz.utils.BearerTokenGenerator

trait MoviesApi extends BearerTokenGenerator {
  val routes = {
    logRequestResult("akka-http") {
      pathPrefix("movies") {
        parameters('id) { id =>
          complete {
            MovieService.get(id).toString
          }
        }
      }
    }
  }
}
